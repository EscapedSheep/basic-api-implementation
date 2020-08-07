package com.thoughtworks.rslist.configuration;

import com.thoughtworks.rslist.service.RsServiceImpl;
import com.thoughtworks.rslist.service.UserServiceImpl;
import com.thoughtworks.rslist.service.VoteServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public RsServiceImpl rsService() {
        return new RsServiceImpl();
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl();
    }

    @Bean
    public VoteServiceImpl voteService() {
        return new VoteServiceImpl();
    }
}
