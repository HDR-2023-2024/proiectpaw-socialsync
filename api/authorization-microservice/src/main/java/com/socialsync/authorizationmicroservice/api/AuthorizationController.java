package com.socialsync.authorizationmicroservice.api;

import com.socialsync.authorizationmicroservice.pojo.UserDto;
import com.socialsync.authorizationmicroservice.service.AuthorizationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authorization")
public class AuthorizationController {
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
      boolean status = authorizationService.login(userDto);

      return new ResponseEntity<>(HttpStatus.OK);
    }


}
