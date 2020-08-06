package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;

import java.util.List;

public interface UserService {

    public int registerUser(User user);

    public List<User> getUserList();

    public void deleteUser(int id);

    public User getUser(int id);
}
