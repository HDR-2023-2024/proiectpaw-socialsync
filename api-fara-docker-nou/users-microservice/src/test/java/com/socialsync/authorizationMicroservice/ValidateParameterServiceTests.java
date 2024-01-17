package com.socialsync.authorizationMicroservice;

import com.socialsync.usersmicroservice.service.ValidateParameterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {ValidateParameterService.class})
@AutoConfigureMockMvc
class ValidateParameterServiceTests {

    @Autowired
    private ValidateParameterService validateParameterService;

    @Test
    void emailValidateTest() throws Exception {
        boolean value = validateParameterService.isValidEmail("madab@gmail.com");

        assertEquals(true,value);
    }

    @Test
    void emailValidateTestFail() throws Exception {
        boolean value = validateParameterService.isValidEmail("cevaEmail");

        assertEquals(false,value);
    }

    @Test
    void usernameValidateTest() throws Exception {
        boolean value = validateParameterService.isValidUsername("madab");

        assertEquals(true,value);
    }

    @Test
    void usernameValidateTestFail() throws Exception {
        boolean value = validateParameterService.isValidUsername("' or 1==1");

        assertEquals(false,value);
    }

    @Test
    void passwordValidateTest() throws Exception {
        boolean value = validateParameterService.isValidPassword("madab");

        assertEquals(true,value);
    }

    @Test
    void passwordValidateTestFail() throws Exception {
        boolean value = validateParameterService.isValidPassword("' or 1==1");

        assertEquals(false,value);
    }
}