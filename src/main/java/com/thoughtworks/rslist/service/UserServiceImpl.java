package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private List<User> userList;

    public UserServiceImpl() {
        userList = new ArrayList<>();
    }

    @Override
    public int registerUser(User user) {
        userList.add(user);
        return userList.size() - 1;
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public Boolean isUserRegistered(String name) {
        List<User> filteredUser = userList.stream().filter(user -> user.getUserName().equals(name)).collect(Collectors.toList());
        return filteredUser.size() > 0;
    }
}
