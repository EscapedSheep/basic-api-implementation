package com.thoughtworks.rslist;


import com.thoughtworks.rslist.api.RsController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Order(1)
    @Test
    public void get_rs_event_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[0].user.age", is(19)))
                .andExpect(jsonPath("$[0].user.gender", is("female")))
                .andExpect(jsonPath("$[0].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[0].user.phone", is("18888888888")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[1].user.age", is(19)))
                .andExpect(jsonPath("$[1].user.gender", is("female")))
                .andExpect(jsonPath("$[1].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[1].user.phone", is("18888888888")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
                .andExpect(jsonPath("$[2].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[2].user.age", is(19)))
                .andExpect(jsonPath("$[2].user.gender", is("female")))
                .andExpect(jsonPath("$[2].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[2].user.phone", is("18888888888")))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void get_rs_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName",is("第一条事件")))
                .andExpect(jsonPath("$.keyWord",is("无标签")))
                .andExpect(jsonPath("$.user.userName", is("xiaowang")))
                .andExpect(jsonPath("$.user.age", is(19)))
                .andExpect(jsonPath("$.user.gender", is("female")))
                .andExpect(jsonPath("$.user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$.user.phone", is("18888888888")))
                .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void get_rs_event_between() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[0].user.age", is(19)))
                .andExpect(jsonPath("$[0].user.gender", is("female")))
                .andExpect(jsonPath("$[0].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[0].user.phone", is("18888888888")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[1].user.age", is(19)))
                .andExpect(jsonPath("$[1].user.gender", is("female")))
                .andExpect(jsonPath("$[1].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[1].user.phone", is("18888888888")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=2&end=3"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[0].user.age", is(19)))
                .andExpect(jsonPath("$[0].user.gender", is("female")))
                .andExpect(jsonPath("$[0].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[0].user.phone", is("18888888888")))
                .andExpect(jsonPath("$[1].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[1].user.age", is(19)))
                .andExpect(jsonPath("$[1].user.gender", is("female")))
                .andExpect(jsonPath("$[1].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[1].user.phone", is("18888888888")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[0].user.age", is(19)))
                .andExpect(jsonPath("$[0].user.gender", is("female")))
                .andExpect(jsonPath("$[0].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[0].user.phone", is("18888888888")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[1].user.age", is(19)))
                .andExpect(jsonPath("$[1].user.gender", is("female")))
                .andExpect(jsonPath("$[1].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[1].user.phone", is("18888888888")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
                .andExpect(jsonPath("$[2].user.userName", is("xiaowang")))
                .andExpect(jsonPath("$[2].user.age", is(19)))
                .andExpect(jsonPath("$[2].user.gender", is("female")))
                .andExpect(jsonPath("$[2].user.email", is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[2].user.phone", is("18888888888")))
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    public void should_add_rs_event() throws Exception {
        String jsonStr = "{\"eventName\":\"猪肉涨价啦\",\"keyWord\":\"经济\"}";
        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(status().isOk());
    }

    @Order(5)
    @Test
    public void should_update_rs_event() throws Exception {
        String jsonStr = "{\"eventName\":\"股票跌啦\",\"keyWord\":\"经济\"}";
        mockMvc.perform(put("/rs/update/2").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(status().isOk());

    }

    @Order(6)
    @Test
    public void should_update_rs_event_name() throws Exception {
        String jsonStr = "{\"eventName\":\"特朗普连任\"}";
        mockMvc.perform(put("/rs/update/1").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(status().isOk());

    }

    @Order(7)
    @Test
    public void should_update_rs_event_key_word() throws Exception {
        String jsonStr = "{\"keyWord\":\"政治\"}";
        mockMvc.perform(put("/rs/update/1").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(status().isOk());

    }

    @Order(8)
    @Test
    public void should_delete_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/rs/delete/3")).andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("政治")))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[2].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(status().isOk());
    }

}
