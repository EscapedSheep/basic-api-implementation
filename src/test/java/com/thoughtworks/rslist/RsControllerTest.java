package com.thoughtworks.rslist;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
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
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void get_rs_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName",is("第一条事件")))
                .andExpect(jsonPath("$.keyWord",is("无标签")))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void get_rs_event_between() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=2&end=3"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    public void should_add_rs_event() throws Exception {
        String jsonStr = "{\"eventName\": \"猪肉涨价啦\", \"keyWord\": \"经济\", \"user\": {\"userName\": \"maida\", \"gender\": \"male\", \"age\": 19, \"phone\": \"18888888888\", \"email\": \"a@gmail.com\"}}";
        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(jsonPath("$[3]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Order(5)
    @Test
    public void should_update_rs_event() throws Exception {
        String jsonStr = "{\"eventName\":\"股票跌啦\",\"keyWord\":\"经济\"}";
        mockMvc.perform(put("/rs/2").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(jsonPath("$[3]", not(hasKey("user"))))
                .andExpect(status().isOk());

    }

    @Order(6)
    @Test
    public void should_update_rs_event_name() throws Exception {
        String jsonStr = "{\"eventName\":\"特朗普连任\"}";
        mockMvc.perform(put("/rs/1").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(jsonPath("$[3]", not(hasKey("user"))))
                .andExpect(status().isOk());

    }

    @Order(7)
    @Test
    public void should_update_rs_event_key_word() throws Exception {
        String jsonStr = "{\"keyWord\":\"政治\"}";
        mockMvc.perform(put("/rs/1").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("政治")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(jsonPath("$[3]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Order(8)
    @Test
    public void should_delete_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("政治")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")))
                .andExpect(jsonPath("$[3]", not(hasKey("user"))))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/rs/3")).andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName",is("特朗普连任")))
                .andExpect(jsonPath("$[0].keyWord",is("政治")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName",is("股票跌啦")))
                .andExpect(jsonPath("$[1].keyWord",is("经济")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName",is("猪肉涨价啦")))
                .andExpect(jsonPath("$[2].keyWord",is("经济")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Order(9)
    @Test
    public void rs_event_name_should_not_null() throws Exception {
        String jsonStr = "{\"keyWord\": \"经济\", \"user\": {\"userName\": \"maida\", \"gender\": \"male\", \"age\": 19, \"phone\": \"18888888888\", \"email\": \"a@gmail.com\"}}";

        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(10)
    @Test
    public void rs_event_key_word_should_not_null() throws Exception {
        String jsonStr = "{\"eventName\": \"猪肉涨价啦\", \"user\": {\"userName\": \"maida\", \"gender\": \"male\", \"age\": 19, \"phone\": \"18888888888\", \"email\": \"a@gmail.com\"}}";

        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(11)
    @Test
    public void rs_event_user_should_not_null() throws Exception {
        String jsonStr = "{\"eventName\": \"猪肉涨价啦\", \"keyWord\": \"经济\"}";

        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(12)
    @Test
    public void should_register_user_when_add_rs_event_if_user_name_not_existed() throws Exception{
        String jsonStr = "{\"eventName\": \"猪肉涨价啦\", \"keyWord\": \"经济\", \"user\": {\"userName\": \"new user\", \"gender\": \"female\", \"age\": 99, \"phone\": \"12345678901\", \"email\": \"a@b.com\"}}";

        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/users"))
                .andExpect((jsonPath("$",hasSize(3))))
                .andExpect(jsonPath("$[2].user_name", is("new user")))
                .andExpect(jsonPath("$[2].user_gender", is("female")))
                .andExpect(jsonPath("$[2].user_age", is(99)))
                .andExpect(jsonPath("$[2].user_phone", is("12345678901")))
                .andExpect(jsonPath("$[2].user_email", is("a@b.com")))
                .andExpect(status().isOk());
    }

    @Order(13)
    @Test
    public void should_return_invalid_index_error() throws Exception {
        mockMvc.perform(get("/rs/-1"))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());

        String jsonStr = "{\"eventName\": \"猪肉涨价啦\", \"keyWord\": \"经济\", \"user\": {\"userName\": \"maida\", \"gender\": \"male\", \"age\": 19, \"phone\": \"18888888888\", \"email\": \"a@gmail.com\"}}";
        mockMvc.perform(put("/rs/999999").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(delete("/rs/-100"))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());

    }

    @Order(14)
    @Test
    public void should_return_invalid_request_param_error() throws Exception {
        mockMvc.perform(get("/rs/list?start=-1&end=2"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/rs/list?start=1&end=999"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());
    }

    @Order(15)
    @Test
    public void should_return_invalid_param_error() throws Exception {
        String jsonStr = "{\"eventName\": \"猪肉涨价啦\", \"keyWord\": \"经济\", \"user\": {\"userName\": \"maidamaidadad\", \"gender\": \"male\", \"age\": 19, \"phone\": \"18888888888\", \"email\": \"a@gmail.com\"}}";

        mockMvc.perform(post("/rs/event").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid param")))
                .andExpect(status().isBadRequest());

    }




}
