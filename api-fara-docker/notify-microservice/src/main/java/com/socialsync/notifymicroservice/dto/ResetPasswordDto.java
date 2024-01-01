package com.socialsync.notifymicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ResetPasswordDto {
    private String destination;
    private String message;
    private String subject;
}
