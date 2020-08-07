package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    private UserDto userDto;

    private User user;

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
        user = User.builder().userName("xiaowang").age(19).email("a@b.com").phone("18888888888").gender("male").build();
        userDto = userRepository.save(user.toUserDto());
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_register_user() throws Exception {
        user.setUserName("newUser");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("id"));

        List<UserDto> findUser = userRepository.findAll();
        assertEquals(findUser.size(), 2);
        assertEquals(userDto.getId(), findUser.get(0).getId());
        assertEquals(userDto.getEmail(), findUser.get(0).getEmail());
        assertEquals(userDto.getAge(), findUser.get(0).getAge());
        assertEquals(userDto.getUserName(), findUser.get(0).getUserName());
        assertEquals(userDto.getPhone(), findUser.get(0).getPhone());
        assertEquals(user.getEmail(), findUser.get(1).getEmail());
        assertEquals(user.getAge(), findUser.get(1).getAge());
        assertEquals(user.getUserName(), findUser.get(1).getUserName());
        assertEquals(user.getPhone(), findUser.get(1).getPhone());
    }

    @Test
    public void name_should_not_null() throws Exception {
        user.setUserName(null);
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void name_length_should_not_over_8() throws Exception {
        user.setUserName("maidamaida66666");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void gender_should_not_null() throws Exception {
        user.setGender(null);
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void age_should_greater_than_17() throws Exception {
        user.setAge(17);
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void age_should_not_over_100() throws Exception {
        user.setAge(101);
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void email_should_fit_email_format() throws Exception {
        user.setEmail("aaa");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void phone_number_should_start_with_1() throws Exception {
        user.setPhone("21234567890");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void phone_number_should_have_length_of_11() throws Exception {
        user.setPhone("123456");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        user.setPhone("1234567890123456");
        userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_get_user_list() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].user_name",is("xiaowang")))
                .andExpect(jsonPath("$[0].user_gender",is("male")))
                .andExpect(jsonPath("$[0].user_age",is(19)))
                .andExpect(jsonPath("$[0].user_phone",is("18888888888")))
                .andExpect(jsonPath("$[0].user_email",is("a@b.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_invalid_user_error() throws Exception {
        user.setAge(10000);
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid user")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_delete_user() throws Exception {
        int id = userDto.getId();
        mockMvc.perform(delete("/user/" + id))
                .andExpect(status().isOk());
        List<UserDto> findUser = userRepository.findAll();
        assertEquals(0,findUser.size());
    }

    @Test
    public void should_return_user_given_user_id() throws Exception {
        int id = userDto.getId();
        mockMvc.perform(get("/user/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_name", is(userDto.getUserName())))
                .andExpect(jsonPath("$.user_gender", is(userDto.getGender())))
                .andExpect(jsonPath("$.user_phone", is(userDto.getPhone())))
                .andExpect(jsonPath("$.user_email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.user_age", is(userDto.getAge())));
    }
}
