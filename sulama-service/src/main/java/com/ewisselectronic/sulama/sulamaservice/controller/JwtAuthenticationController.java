package com.ewisselectronic.sulama.sulamaservice.controller;

import com.ewisselectronic.sulama.sulamacore.model.User;
import com.ewisselectronic.sulama.sulamacore.service.AppUserDetailsService;
import com.ewisselectronic.sulama.sulamacore.service.UserService;
import com.ewisselectronic.sulama.sulamacore.utils.AppUserPrincipal;
import com.ewisselectronic.sulama.sulamaservice.config.JwtTokenUtil;
import com.ewisselectronic.sulama.sulamaservice.model.auth.JwtBadResponse;
import com.ewisselectronic.sulama.sulamaservice.model.auth.JwtRequest;
import com.ewisselectronic.sulama.sulamaservice.model.auth.JwtResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Objects;

@RestController
@CrossOrigin
@AllArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final AppUserDetailsService appUserDetailsService;
    private final UserDetailsService jwtInMemoryUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        return authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

    private ResponseEntity<?> authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            final AppUserPrincipal userDetails = appUserDetailsService.loadUserByUsername(username);
            final String token = jwtTokenUtil.generateToken(userDetails);

            User user = userService.get(username);
            if(user != null) {
                user.setLastInteractionTime(new Timestamp(System.currentTimeMillis()));
                user.setLoginCount(user.getLoginCount() != null ? user.getLoginCount()+1 : 1);
                userService.save(user);
            }

            return ResponseEntity.ok(new JwtResponse(userDetails.getId(), username, userDetails.getFullName(), token, userDetails.isAdmin()));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON_UTF8).body(new JwtBadResponse("user_disabled"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON_UTF8).body(new JwtBadResponse("bad_credentials"));
        }
    }

}
