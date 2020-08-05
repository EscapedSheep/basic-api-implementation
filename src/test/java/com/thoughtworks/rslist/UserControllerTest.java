package com.thoughtworks.rslist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void should_register_user() throws Exception {
        User user = getTestUser();
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Order(2)
    @Test
    public void name_should_not_null() throws Exception {
        User user = getTestUser();
        user.setUserName(null);
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(3)
    @Test
    public void name_length_should_not_over_8() throws Exception {
        User user = getTestUser();
        user.setUserName("maidamaida66666");
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(4)
    @Test
    public void gender_should_not_null() throws Exception {
        User user = getTestUser();
        user.setGender(null);
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(5)
    @Test
    public void age_should_greater_than_17() throws Exception {
        User user = getTestUser();
        user.setAge(17);
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(6)
    @Test
    public void age_should_not_over_100() throws Exception {
        User user = getTestUser();
        user.setAge(101);
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(7)
    @Test
    public void email_should_fit_email_format() throws Exception {
        User user = getTestUser();
        user.setEmail("aaa");
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(8)
    @Test
    public void phone_number_should_start_with_1() throws Exception {
        User user = getTestUser();
        user.setPhone("21234567890");
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(9)
    @Test
    public void phone_number_should_have_length_of_11() throws Exception {
        User user = getTestUser();
        user.setPhone("123456");
        String userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        user.setPhone("1234567890123456");
        userJson = getUserJson(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(10)
    @Test
    public void should_get_user_list() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].userName",is("maida")))
                .andExpect(jsonPath("$[1].gender",is("male")))
                .andExpect(jsonPath("$[1].age",is(99)))
                .andExpect(jsonPath("$[1].phone",is("18888888888")))
                .andExpect(jsonPath("$[1].email",is("email@gmail.com")))
                .andExpect(status().isOk());
    }

    private User getTestUser() {
        User user = new User("maida", "male", 99, "18888888888", "email@gmail.com");
        return user;
    }

    private String getUserJson(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(user);
    }
}
