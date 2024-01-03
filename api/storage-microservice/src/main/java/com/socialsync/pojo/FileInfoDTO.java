package com.socialsync.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FileInfoDTO {
    private String id;

    private String filename;

    private boolean isConfirmed;

    private LocalDateTime dateCreated;

    private byte[] content;

    public FileInfoDTO(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }
}
