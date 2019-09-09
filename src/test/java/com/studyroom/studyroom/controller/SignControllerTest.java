package com.studyroom.studyroom.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest // Spring Boot의 Configuration들을 자동으로 설정할 수 있다.
@AutoConfigureMockMvc
@Transactional
public class SignControllerTest {

    /*
    *   < MockMvc method >
        ( perform )
        주어진 url을 수행할수 있는 환경을 구성해 줍니다.
        GET, POST, PUT, DELETE 등 다양한 method를 처리 가능합니다.
        header에 값을 세팅하거나 AcceptType 설정을 지원합니다.
        mockMvc.perform(post(“/v1/signin”).params(params)

        ( andDo )
        perform 요청을 처리합니다. andDo(print())를 하면 처리 결과를 console 화면에서도 볼수 있습니다.

        ( andExpect )
        검증 내용을 체크합니다.
        결과가 200 OK 인지 체크 – andExpect(status().isOK())
        결과 데이터가 Json인 경우 다음과 같이 체크 가능합니다.
        .andExpect(jsonPath(“$.success”).value(true))

        ( andReturn )
        테스트 완료 후 결과 객체를 받을수 있습니다. 후속 작업이 필요할때 용이 합니다.
        MvcResult result = mockMvc.perform(post(“/v1/signIn”).params(params)).andDo(print());
    *
    * */
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signIn() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "halfdev@gmail.com");
        params.add("password", "1234");
        mockMvc.perform(post("/v1/signIn").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void signUp() throws Exception {
        long epochTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "halfdev_" + epochTime + "@gmail.com");
        params.add("password", "12345");
        params.add("name", "halfdev_" + epochTime);
        mockMvc.perform(post("/v1/signUp").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

    // ADMIN 권한을 생성해서 가상의 회원에게 USER 권한만 가능한 리소스를 요청했을 때 access denied 되도록 TEST
    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN"})
    public void accessdenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/exception/accessdenied"));
    }
}
