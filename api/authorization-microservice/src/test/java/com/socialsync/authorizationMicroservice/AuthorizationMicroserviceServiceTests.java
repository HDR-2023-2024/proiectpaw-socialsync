package com.socialsync.authorizationMicroservice;

import com.socialsync.usersmicroservice.pojo.AuthorizedInfo;
import com.socialsync.usersmicroservice.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {JWTService.class})
@AutoConfigureMockMvc
class AuthorizationMicroserviceServiceTests {

    @Autowired
    private JWTService jwtService;

    // decodare cu succes
    @Test
    void generateAccessTokenTest() throws Exception {
        String token = jwtService.generateAccessToken("1", "admin");
        AuthorizedInfo authorizedInfo = jwtService.decodeToken(token);

        assertEquals("admin", authorizedInfo.getRole());
    }

    // imi da rol ok
    @Test
    void generateAccessTokenTestFail() throws Exception {
        String token = jwtService.generateAccessToken("1", "admin");
        AuthorizedInfo authorizedInfo = jwtService.decodeToken(token);

        assertNotEquals("user", authorizedInfo.getRole());
    }

    // invalidare token => exceptie
    @Test
    void generateAccessTokenTestException() {
        String token = "";
        // nu arunca niciodata exceptie
        try {
            token = jwtService.generateAccessToken("1", "admin");
        }catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        String finalToken = token;
        assertThrows(Exception.class, () -> {
            AuthorizedInfo authorizedInfo = jwtService.decodeToken(finalToken + "a");
        });
    }
}