package com.socialsync.notifymicroservice.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@Setter
@Getter
public class ContactForm {
    private String nume;
    private String prenume;
    private String email;
    private String subject;
    private String message;
}

