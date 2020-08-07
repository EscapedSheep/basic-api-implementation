package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public int registerUser(User user) {
        UserDto userDto = userRepository.save(user.toUserDto());
        return userDto.getId();
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll().stream().map(userDto -> userDto.toUser()).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).get().toUser();
    }
}
