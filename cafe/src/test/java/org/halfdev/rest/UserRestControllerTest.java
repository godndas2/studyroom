package org.halfdev.rest;

import org.halfdev.utils.AbstractRestControllerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.halfdev.utils.LogInUtils.getTokenForLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserRestControllerTest extends AbstractRestControllerTest {

    @BeforeAll
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getActiveUserForUserWithToken() throws Exception {

        final String token = getTokenForLogin("user", "password", getMockMvc());

        getMockMvc().perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\n" +
                                "  \"username\" : \"user\",\n" +
                                "  \"firstname\" : \"user\",\n" +
                                "  \"lastname\" : \"user\",\n" +
                                "  \"email\" : \"enabled@user.com\",\n" +
                                "  \"authorities\" : [ {\n" +
                                "    \"name\" : \"ROLE_USER\"\n" +
                                "  } ]\n" +
                                "}"
                ));
    }
}
