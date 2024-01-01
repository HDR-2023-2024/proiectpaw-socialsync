package com.socialsync.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "filename", length = 100, nullable = false)
    private String filename;

    // e stocat dupa generarea id-ului
    @Column(name = "path", length = 1000)
    private String path;

    @Lob
    @Column(name = "content", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] content;


    public FileInfo(String filename, String path,byte[] content) {
        this.filename = filename;
        this.path = path;
        this.content = content;
    }
}