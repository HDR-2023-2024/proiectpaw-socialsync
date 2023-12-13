package com.socialsync.api;

import com.socialsync.pojo.FileInfo;
import com.socialsync.pojo.JsonFile;
import com.socialsync.pojo.UrlDto;
import com.socialsync.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/storage")
@AllArgsConstructor
public class StorageController {
    private FileRepository fileRepository;

    @GetMapping("/img/{id}")
    public ResponseEntity<byte[]> getMemoryFileByIdImg(@PathVariable("id") String id) {
        Optional<FileInfo> optionalFile = this.fileRepository.findById(id);
        if (!optionalFile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(optionalFile.get().getContent(), headers, HttpStatus.OK);
        }
    }

    @PostMapping("/upload-multipartFile")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        // se trece prin fiecare fisier
        List<UrlDto> url = new ArrayList<>();

        if (files.size() < 1) {
            return ResponseEntity.ok("Nu sa trimis nici o imagine pentru a fi salvata!");
        }
        for (MultipartFile file : files) {
            try {
                FileInfo fileInfo = fileRepository.save(new FileInfo(file.getOriginalFilename(), null, file.getBytes()));
                System.out.println(file.getOriginalFilename());
                url.add(new UrlDto("http://localhost:8088/api/v1/storage/img/" + fileInfo.getId()));
            } catch (IOException ioException) {
                System.out.print(ioException.getMessage());
                return ResponseEntity.status(500).body("Eroare la salvarea fisierelor.");
            }
        }

        return new ResponseEntity(url, HttpStatus.OK);
    }

    @PostMapping("/upload-json")
    public ResponseEntity<?> uploadFilesJSON(@RequestBody List<JsonFile> files) {
        // se trece prin fiecare fisier
        List<UrlDto> url = new ArrayList<>();
        if (files.size() < 1) {
            return ResponseEntity.ok("Nu sa trimis nici o imagine pentru a fi salvata!");
        }
        for (JsonFile file : files) {
            FileInfo fileInfo = fileRepository.save(new FileInfo(file.getFileName(), null, file.getContent()));
            System.out.println(file.getFileName());
            url.add(new UrlDto("http://localhost:8088/api/v1/storage/img/" + fileInfo.getId()));
        }

        return new ResponseEntity(url, HttpStatus.OK);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<JsonFile> getMemoryFileById(@PathVariable("id") String id) {
        Optional<FileInfo> optionalFile = this.fileRepository.findById(id);
        if (!optionalFile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new JsonFile(optionalFile.get().getFilename(), optionalFile.get().getContent()), HttpStatus.OK);
        }
    }*/

    @DeleteMapping("/delete-file/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable String fileId) {
        Optional<FileInfo> optionalFileInfo = fileRepository.findById(fileId);

        if (optionalFileInfo.isPresent()) {
            fileRepository.deleteById(fileId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
