package com.ewisselectronic.sulama.sulamaservice.controller;

import com.ewisselectronic.sulama.sulamacore.model.Role;
import com.ewisselectronic.sulama.sulamacore.model.User;
import com.ewisselectronic.sulama.sulamacore.service.UserService;
import com.ewisselectronic.sulama.sulamaservice.model.ChangePassword;
import com.ewisselectronic.sulama.sulamaservice.model.SaveResponse;
import com.ewisselectronic.sulama.sulamaservice.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "pageSize", required = false) Optional<Integer> pageSize,
                                         @RequestParam(value = "pageNumber", required = false) Optional<Integer> pageNumber,
                                         @RequestParam(value = "sortBy", required = false) Optional<String> sortBy,
                                         @RequestParam(value = "direction", required = false) Optional<String> direction,
                                         @RequestParam(value = "search", required = false) Optional<String> search,
                                         @RequestParam(value = "enabledState", required = false) Optional<Integer> status) {

        Page<User> userPage = userService.getAll(pageNumber.orElse(0), pageSize.orElse(10),
                sortBy.orElse("username"), direction.orElse("ASC"), search.orElse(""), status.orElse(1));

        return ResponseEntity.ok(userPage);
    }

    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public SaveResponse saveUser(@Valid @RequestBody UserRequest formUser) {
        try {
            String state = "created";
            User user = new User();
            if(formUser.getId() != null) {
                user = userService.get(formUser.getId());
                state = "updated";
            }

            user.setName(formUser.getName());
            user.setSurname(formUser.getSurname());
            user.setUsername(formUser.getUsername());
            List<Role> roles = new ArrayList<>();
            Role standardRole = new Role();
            standardRole.setId(1);
            roles.add(standardRole);
            if(formUser.isAdmin()) {
                Role adminRole = new Role();
                adminRole.setId(2);
                roles.add(adminRole);
            }
            if (formUser.getPassword() != null && !formUser.getPassword().equals("") && !formUser.getPassword().isEmpty()) {
                user.setPassword(BCrypt.hashpw(formUser.getPassword(), BCrypt.gensalt()));
            }
            user.setRoles(roles);

            user.setEnabled(formUser.isEnabled());
            user.setAdmin(formUser.isAdmin());

            if (user.getId() == null) {
                userService.save(user);
            } else {
                userService.update(user);
            }
            return new SaveResponse(true, String.format("User %s %s successfully", user.getUsername(), state), (long) user.getId());
        } catch (Exception e) {
            return new SaveResponse(false, e.getCause().getCause().getMessage(), null);
        }
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public User getUserById(@PathVariable("userId") Integer userId) {
        return userService.get(userId);
    }
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public User getUser(@RequestAttribute int userId) {
        return userService.get(userId);
    }


    @PostMapping(value = "/changePassword", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public SaveResponse changePassword(@RequestAttribute int userId,@Valid @RequestBody ChangePassword changePassword) {
        try {
            if (!changePassword.getPassword().equals(changePassword.getPasswordConfirm()))
                return new SaveResponse(false, "Password & password confirm must be identical", null);

            User user = userService.get(userId);
            user.setPassword(BCrypt.hashpw(changePassword.getPassword(), BCrypt.gensalt()));
            userService.save(user);
            return new SaveResponse(true, "Password has been changed successfully for user " + user.getName(), null);

        } catch (Exception e) {
            return new SaveResponse(false, e.getMessage(), null);
        }
    }

}
