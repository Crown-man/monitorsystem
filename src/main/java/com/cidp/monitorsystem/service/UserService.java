package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.UserMapper;
import com.cidp.monitorsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public boolean verification(User user) {
        User password = userMapper.getPassword(user.getUsername());
        if (password==null){
            return false;
        }
        return user.getPassword().equals(password.getPassword());
    }
}
