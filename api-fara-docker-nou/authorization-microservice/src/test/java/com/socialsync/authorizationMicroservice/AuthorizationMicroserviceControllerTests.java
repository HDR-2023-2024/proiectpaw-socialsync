package com.socialsync.authorizationMicroservice;

import com.socialsync.usersmicroservice.AuthorizationMicroserviceApplication;
import com.socialsync.usersmicroservice.pojo.AuthorizedInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AuthorizationMicroserviceApplication.class)
@AutoConfigureMockMvc
public class AuthorizationMicroserviceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    /*@Test
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/api/v1/authorization/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }*/

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void generateJWTTest() throws Exception {
        AuthorizedInfo authorizationInfo = new AuthorizedInfo();
        authorizationInfo.setId("1");
        authorizationInfo.setRole("admin");

        String requestBody = objectMapper.writeValueAsString(authorizationInfo);

        this.mockMvc.perform(post("/api/v1/authorization/generateJWT")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void validateJWTSuccess() throws Exception {
        // generare token
        AuthorizedInfo authorizationInfo = new AuthorizedInfo();
        authorizationInfo.setId("1");
        authorizationInfo.setRole("admin");

        String requestBody = objectMapper.writeValueAsString(authorizationInfo);

        ResultActions resultActions = this.mockMvc.perform(post("/api/v1/authorization/generateJWT")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // extragere din header
        String generatedToken = resultActions.andReturn().getResponse().getHeader("Authorization");

        // validare
        this.mockMvc.perform(post("/api/v1/authorization/validateJWT")
                        .content(requestBody)
                        .header("Authorization", generatedToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void validateJWTFail() throws Exception {
        // generare token
        AuthorizedInfo authorizationInfo = new AuthorizedInfo();
        authorizationInfo.setId("1");
        authorizationInfo.setRole("admin");

        String requestBody = objectMapper.writeValueAsString(authorizationInfo);

        ResultActions resultActions = this.mockMvc.perform(post("/api/v1/authorization/generateJWT")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // extragere din header
        String generatedToken = resultActions.andReturn().getResponse().getHeader("Authorization") + "a";

        // validare
        this.mockMvc.perform(post("/api/v1/authorization/validateJWT")
                        .content(requestBody)
                        .header("Authorization", generatedToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
