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
import java.time.format.DateTimeFormatter;

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

        for(int i = 1; i < 9; i++){
            VoteDto voteDto = VoteDto.builder().voteNum(i).rsEvent(rsEventDto).user(userDto).build();
            voteRepository.save(voteDto);
        }
    }

    @Test
    void should_get_vote_record() throws Exception {
        mockMvc.perform(get("/voteRecord")
                .param("userId", String.valueOf(userDto.getId()))
                .param("rsEventId", String.valueOf(rsEventDto.getId()))
                .param("pageIndex", "1"))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(1)))
                .andExpect(jsonPath("$[1].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[1].voteNum", is(2)))
                .andExpect(jsonPath("$[2].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[2].voteNum", is(3)))
                .andExpect(jsonPath("$[3].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[3].voteNum", is(4)))
                .andExpect(jsonPath("$[4].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[4].voteNum", is(5)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/voteRecord")
                .param("userId", String.valueOf(userDto.getId()))
                .param("rsEventId", String.valueOf(rsEventDto.getId()))
                .param("pageIndex", "2"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(6)))
                .andExpect(jsonPath("$[1].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[1].voteNum", is(7)))
                .andExpect(jsonPath("$[2].userId", is(userDto.getId())))
                .andExpect(jsonPath("$[2].voteNum", is(8)))
                .andExpect(status().isOk());
    }

    @Test
    void should_reject_vote_when_user_votes_is_less_than_given_votes_number() throws Exception {
        Vote vote = Vote.builder().voteNum(11).rsEventId(rsEventDto.getId()).userId(userDto.getId()).build();
        String jsonStr = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/vote/" + rsEventDto.getId()).content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Vote number not enough")));
    }

    @Test
    void should_get_vote_records_between_given_time() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String startTimeString = "2001-01-31 12:12:12";
        String endTimeString = "2099-01-31 12:12:12";
        mockMvc.perform(get("/voteRecord")
                        .param("start", startTimeString)
                        .param("end", endTimeString))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(8)))
                        .andExpect(jsonPath("$[0].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[0].voteNum", is(1)))
                        .andExpect(jsonPath("$[1].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[1].voteNum", is(2)))
                        .andExpect(jsonPath("$[2].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[2].voteNum", is(3)))
                        .andExpect(jsonPath("$[3].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[3].voteNum", is(4)))
                        .andExpect(jsonPath("$[4].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[4].voteNum", is(5)))
                        .andExpect(jsonPath("$[5].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[5].voteNum", is(6)))
                        .andExpect(jsonPath("$[6].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[6].voteNum", is(7)))
                        .andExpect(jsonPath("$[7].userId", is(userDto.getId())))
                        .andExpect(jsonPath("$[7].voteNum", is(8)));
    }
}