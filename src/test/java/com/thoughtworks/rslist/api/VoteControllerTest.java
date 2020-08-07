package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private MockMvc mockMvc;

    private UserDto userDto;
    private RsEventDto rsEventDto;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        objectMapper = new ObjectMapper();

        userDto = UserDto.builder().userName("xiaowang").age(19).email("a@b.com").phone("18888888888").gender("male").votes(10).build();
        userDto = userRepository.save(userDto);

        rsEventDto = RsEventDto.builder().userDto(userDto).eventName("猪肉涨价啦").keyWord("经济").build();
        rsEventDto = rsEventRepository.save(rsEventDto);

        VoteDto voteDto = VoteDto.builder().voteNum(5).rsEvent(rsEventDto).user(userDto).build();
        voteRepository.save(voteDto);
    }

    @Test
    void should_get_vote_record() throws Exception {
        mockMvc.perform(get("/voteRecord")
                .param("userId", String.valueOf(userDto.getId()))
                .param("rsEventId", String.valueOf(rsEventDto.getId())))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(5)))
                .andExpect(status().isOk());
    }

    @Test
    void should_reject_vote_when_user_votes_is_less_than_given_votes_number() throws Exception {
        //objectMapper.configure(MapperFeature.USE_ANNOTATIONS,false);
        Vote vote = Vote.builder().voteNum(11).rsEventId(rsEventDto.getId()).userId(userDto.getId()).build();
        String jsonStr = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/vote/" + rsEventDto.getId()).content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Vote number not enough")));
    }
}