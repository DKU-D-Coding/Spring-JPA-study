package com.dku.springstudy.controller;

import com.dku.springstudy.service.MemberService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc; // mockMvc 생성

    @Autowired
    MemberService memberService;

    @Test
    void membership() throws Exception {
        //given
        String requestJson = "{\"email\":\"example@gmail.com\", \"password\": \"1234\", \"name\": \"jaem\"}";
        String result =  mockMvc.perform(post("/membership")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        System.out.println("result = " + result);
    }

    @Test
    void withDraw() throws Exception {
        //given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYWVtIiwiaWF0IjoxNjc1NTg1NDEzLCJleHAiOjE2NzU1ODcyMTN9.lF8ROm63BVQkxI0mrYEl2Ajr-JB8vvPIJgOvsf_Sq_s";
        String requestJson = "{\"loginAccessToken\":\""+token+"\"}";
        String result = mockMvc.perform(post("/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andReturn().getResponse().getContentAsString();
        System.out.println("result = " + result);
    }

    @Test
    void login() throws Exception {

        membership();

        String requestJson = "{\"email\":\"example@gmail.com\", \"password\": \"1\", \"name\": \"jaem\"}";

        String result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andReturn().getResponse().getContentAsString();

        System.out.println("result = " + result);

    }


}
