package com.socialsync.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoSQL {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "filename", length = 100, nullable = false)
    private String filename;

    @Column(name = "isConfirmed", length = 1, nullable = false)
    private boolean isConfirmed;

    @Column(name = "dateCreated", nullable = false)
    private LocalDateTime dateCreated;


    public FileInfoSQL(String filename, byte[] content) {
        this.filename = filename;
    }
}
