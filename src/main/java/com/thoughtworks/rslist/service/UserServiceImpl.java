package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private List<User> userList;

    public UserServiceImpl() {
        userList = new ArrayList<>();
    }

    @Override
    public void registerUser(User user) {
        userList.add(user);
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }
}
