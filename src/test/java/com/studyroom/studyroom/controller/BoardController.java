package com.studyroom.studyroom.controller;

import com.studyroom.studyroom.repository.board.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    private String token;

    @Test
    public void findPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/board/{boardName}/posts")
        .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").exists());
    }
}
