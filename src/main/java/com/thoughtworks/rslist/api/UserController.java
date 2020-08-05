package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private List<User> userList = new ArrayList<>();

    @PostMapping("/user")
    public void registerUser(@RequestBody @Valid User user) {
        userList.add(user);
    }

    @GetMapping("/user/{userName}")
    public Boolean isUserRegistered(@PathVariable String userName) {
        List<User> filteredUser = userList.stream().filter(user -> user.getUserName().equals(userName)).collect(Collectors.toList());
        return filteredUser.size() > 0;
    }
}
