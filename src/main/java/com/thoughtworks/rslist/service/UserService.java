package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;

import java.util.List;

public interface UserService {

    public void registerUser(User user);

    public List<User> getUserList();
}
