package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.MapperFeature.USE_ANNOTATIONS;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private UserDto userDto;
    private User user;
    private RsEventDto rsEventDto;
    private RsEvent rsEvent;
    private RsEventDto secondRsEventDto;
    private RsEvent secondRsEvent;
    private RsEventDto thirdRsEventDto;
    private RsEvent thirdRsEvent;

    private ObjectMapper objectMapper;

    @Autowired
    private RsEventRepository rsEventRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        objectMapper = new ObjectMapper();
        user = User.builder().userName("xiaowang").age(19).email("a@b.com").phone("18888888888").gender("male").votes(10).build();
        userDto = userRepository.save(user.toUserDto());

        rsEvent = RsEvent.builder().eventName("猪肉涨价啦").keyWord("经济").userId(userDto.getId()).build();
        secondRsEvent = RsEvent.builder().eventName("特朗普连任").keyWord("政治").userId(userDto.getId()).build();
        thirdRsEvent = RsEvent.builder().eventName("股票跌啦").keyWord("经济").userId(userDto.getId()).build();

        rsEventDto = rsEvent.toRsEventDto(userDto);
        secondRsEventDto = secondRsEvent.toRsEventDto(userDto);
        thirdRsEventDto = thirdRsEvent.toRsEventDto(userDto);

        rsEventDto = rsEventRepository.save(rsEventDto);
        secondRsEventDto = rsEventRepository.save(secondRsEventDto);
        thirdRsEventDto = rsEventRepository.save(thirdRsEventDto);

        rsEvent.setRsEventId(rsEventDto.getId());
        secondRsEvent.setRsEventId(secondRsEventDto.getId());
        thirdRsEvent.setRsEventId(thirdRsEventDto.getId());
    }

    @Test
    public void get_rs_event_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价啦")))
                .andExpect(jsonPath("$[0].keyWord",is("经济")))
                .andExpect(jsonPath("$[0].userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(rsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[1].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(jsonPath("$[2].eventName", is("股票跌啦")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].userId", is(thirdRsEvent.getUserId())))
                .andExpect(jsonPath("$[2].voteNum", is(thirdRsEvent.getVoteNum())))
                .andExpect(status().isOk());
    }

    @Test
    public void get_rs_event() throws Exception {
        int id = rsEventDto.getId();
        mockMvc.perform(get("/rs/" + id))
                .andExpect(jsonPath("$.eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$.keyWord",is("经济")))
                .andExpect(jsonPath("$.userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$.voteNum", is(rsEvent.getVoteNum())))
                .andExpect(status().isOk());
    }

    @Test
    public void get_rs_event_between() throws Exception {
        int firstIndex = rsEventDto.getId();
        int secondIndex = secondRsEventDto.getId();
        int thirdIndex = thirdRsEventDto.getId();
        mockMvc.perform(get("/rs/list?start=" + firstIndex + "&end=" + secondIndex))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价啦")))
                .andExpect(jsonPath("$[0].keyWord",is("经济")))
                .andExpect(jsonPath("$[0].userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(rsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[1].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list?start=" + secondIndex + "&end=" + thirdIndex))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("政治")))
                .andExpect(jsonPath("$[0].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[1].userId", is(thirdRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(thirdRsEvent.getVoteNum())))

                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list?start=" + firstIndex + "&end=" + thirdIndex))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价啦")))
                .andExpect(jsonPath("$[0].keyWord",is("经济")))
                .andExpect(jsonPath("$[0].userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(rsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[1].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(jsonPath("$[2].eventName", is("股票跌啦")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].userId", is(thirdRsEvent.getUserId())))
                .andExpect(jsonPath("$[2].voteNum", is(thirdRsEvent.getVoteNum())))
                .andExpect(status().isOk());
    }

    @Test
    public void should_add_rs_event_when_user_existed() throws Exception {
        rsEventRepository.deleteAll();
        objectMapper.configure(USE_ANNOTATIONS, false);
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").contentType(MediaType.APPLICATION_JSON).content(jsonStr))
                .andExpect(status().isCreated())
                .andExpect(header().exists("id"));

        List<RsEventDto> allRsEvent = rsEventRepository.findAll();
        assertNotNull(allRsEvent);
        assertEquals(1, allRsEvent.size());
        assertEquals(rsEvent.getEventName(), allRsEvent.get(0).getEventName());
    }

    @Test
    void should_return_bad_request_when_user_not_existed() throws Exception{
        rsEvent.setUserId(999);
        objectMapper.configure(USE_ANNOTATIONS, false);
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").contentType(MediaType.APPLICATION_JSON).content(jsonStr))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_update_rs_event() throws Exception {
        rsEvent.setEventName("Updated name");
        objectMapper.configure(USE_ANNOTATIONS, false);
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rs/" + rsEvent.getRsEventId()).content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<RsEventDto> rsEventDto = rsEventRepository.findById(rsEvent.getRsEventId());
        assertEquals(rsEventDto.get().getEventName(), "Updated name");
    }

    @Test
    public void should_update_rs_event_name() throws Exception {
        rsEvent.setKeyWord(null);
        rsEvent.setEventName("updatedName");
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rs/" + rsEvent.getRsEventId()).content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("updatedName")))
                .andExpect(jsonPath("$[0].keyWord",is("经济")))
                .andExpect(jsonPath("$[0].userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(rsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[1].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(jsonPath("$[2].eventName", is("股票跌啦")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].userId", is(thirdRsEvent.getUserId())))
                .andExpect(jsonPath("$[2].voteNum", is(thirdRsEvent.getVoteNum())))
                .andExpect(status().isOk());

    }

    @Test
    public void should_update_rs_event_key_word() throws Exception {
        rsEvent.setEventName(null);
        rsEvent.setKeyWord("updatedKeyWord");
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rs/" + rsEvent.getRsEventId()).content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价啦")))
                .andExpect(jsonPath("$[0].keyWord",is("updatedKeyWord")))
                .andExpect(jsonPath("$[0].userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(rsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[1].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(jsonPath("$[2].eventName", is("股票跌啦")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].userId", is(thirdRsEvent.getUserId())))
                .andExpect(jsonPath("$[2].voteNum", is(thirdRsEvent.getVoteNum())))
                .andExpect(status().isOk());
    }

    @Test
    public void should_delete_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价啦")))
                .andExpect(jsonPath("$[0].keyWord",is("经济")))
                .andExpect(jsonPath("$[0].userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(rsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[1].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(jsonPath("$[2].eventName", is("股票跌啦")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].userId", is(thirdRsEvent.getUserId())))
                .andExpect(jsonPath("$[2].voteNum", is(thirdRsEvent.getVoteNum())))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/rs/" + thirdRsEvent.getRsEventId())).andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("猪肉涨价啦")))
                .andExpect(jsonPath("$[0].keyWord",is("经济")))
                .andExpect(jsonPath("$[0].userId", is(rsEvent.getUserId())))
                .andExpect(jsonPath("$[0].voteNum", is(rsEvent.getVoteNum())))
                .andExpect(jsonPath("$[1].eventName", is("特朗普连任")))
                .andExpect(jsonPath("$[1].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].userId", is(secondRsEvent.getUserId())))
                .andExpect(jsonPath("$[1].voteNum", is(secondRsEvent.getVoteNum())))
                .andExpect(status().isOk());
    }

    @Test
    public void rs_event_name_should_not_null_when_add_rs_event() throws Exception {
        rsEvent.setEventName(null);
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
    }

    @Test
    public void rs_event_key_word_should_not_null() throws Exception {
        rsEvent.setKeyWord(null);
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void rs_event_user_id_should_not_null() throws Exception {
        String jsonStr = "{\"eventName\": \"猪肉涨价啦\", \"keyWord\": \"经济\"}";
        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_reject_add_rs_event_when_user_not_existed() throws Exception{
        rsEvent.setUserId(9999);
        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_invalid_index_error() throws Exception {
        mockMvc.perform(get("/rs/-1"))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());

        String jsonStr = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rs/99999999").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(delete("/rs/-100"))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void should_return_invalid_request_param_error() throws Exception {
        mockMvc.perform(get("/rs/list?start=-1&end=2"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/rs/list?start=1&end=-1"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/rs/list?start=2&end=1"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());
    }
}
