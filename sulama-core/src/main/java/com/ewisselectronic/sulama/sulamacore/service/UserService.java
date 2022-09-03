package com.ewisselectronic.sulama.sulamacore.service;

import com.ewisselectronic.sulama.sulamacore.annotation.AspectLogged;
import com.ewisselectronic.sulama.sulamacore.model.User;
import com.ewisselectronic.sulama.sulamacore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AspectLogged
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public User get(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User get(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param direction
     * @param search
     * @param enabledState : 0 all 1 true 2 false
     * @return
     */
    public Page<User> getAll(Integer pageNo, Integer pageSize, String sortBy, String direction, String search, Integer enabledState) {
        List<Boolean> statusList = new ArrayList<>();
        if(enabledState == 0) {
            statusList.add(true);
            statusList.add(false);
        } else if(enabledState == 1) {
            statusList.add(true);
        } else if(enabledState == 2) {
            statusList.add(false);
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.valueOf(direction), sortBy));
        if(search == null || search == "") {
            return userRepository.findByEnabled(statusList, paging);
        } else {
            search = "%" + search + "%";
            return userRepository.findByAnyColumnContaining(search, statusList, paging);
        }
    }


}
