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

    @GetMapping
    public ResponseEntity<?> fetchAllUsers(@RequestHeader(name = "X-User-Id", required = false) String userId, @RequestHeader(name = "X-User-Role", required = false) String userRole) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/upload-multipartFile")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        // se trece prin fiecare fisier
        String director = "storage/paw-img";
        List<UrlDto> url = new ArrayList<>();
        File directory = new File(director);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                return ResponseEntity.status(500).body("Eroare la crearea directorului de incarcare a imaginilor.");
            }
        }
        if (files.size() < 1) {
            return ResponseEntity.ok("Nu sa trimis nici o imagine pentru a fi salvata!");
        }
        for (MultipartFile file : files) {
            try {// file.getBytes continut ca array
                FileInfo fileInfo = fileRepository.save(new FileInfo(file.getOriginalFilename(), null));
                byte[] fileBytes = file.getBytes();
                System.out.println(file.getOriginalFilename());
                String filePath = director + "/" + fileInfo.getId();
                FileOutputStream fos = new FileOutputStream(filePath + ".bin");
                url.add(new UrlDto("http://localhost:8088/api/v1/storage/img/" + fileInfo.getId()));
                fos.write(fileBytes);
            } catch (IOException ioException) {
                System.out.print(ioException.getMessage());
                return ResponseEntity.status(500).body("Eroare la scrierea fisierelor.");
            }
        }

        return new ResponseEntity(url,HttpStatus.OK);
    }

    @PostMapping("/upload-json")
    public ResponseEntity<?> uploadFilesJSON( @RequestBody List<JsonFile> files) {
        // se trece prin fiecare fisier
        String director = "storage/paw-img";
        List<UrlDto> url = new ArrayList<>();
        File directory = new File(director);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                return ResponseEntity.status(500).body("Eroare la crearea directorului de incarcare a imaginilor.");
            }
        }
        if (files.size() < 1) {
            return ResponseEntity.ok("Nu sa trimis nici o imagine pentru a fi salvata!");
        }
        for (JsonFile file : files) {
            try {// file.getBytes continut ca array
                FileInfo fileInfo = fileRepository.save(new FileInfo(file.getFileName(), null));
                byte[] fileBytes = file.getContent();
                System.out.println(file.getFileName());
                String filePath = director + "/" + fileInfo.getId();
                FileOutputStream fos = new FileOutputStream(filePath + ".bin");
                url.add(new UrlDto("http://localhost:8088/api/v1/storage/img/" + fileInfo.getId()));
                fos.write(fileBytes);
            } catch (IOException ioException) {
                System.out.print(ioException.getMessage());
                return ResponseEntity.status(500).body("Eroare la scrierea fisierelor.");
            }
        }

        return new ResponseEntity(url,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonFile> getMemoryFileById(@PathVariable("id") String id) {
        Optional<FileInfo> optionalFile = this.fileRepository.findById(id);
        String director = "storage/paw-img";
        if(!optionalFile.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            try {
                File file = new File(director + "/" + id + ".bin");
                long fileSize = file.length();

                byte[] content = new byte[(int) fileSize];

                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    int bytesRead = fileInputStream.read(content);
                    // array content disponibil
                    return new ResponseEntity<>(new JsonFile(optionalFile.get().getFilename(), content),HttpStatus.OK);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @GetMapping("/img/{id}")
    public ResponseEntity<byte[]> getMemoryFileByIdImg(@PathVariable("id") String id) {
        Optional<FileInfo> optionalFile = this.fileRepository.findById(id);
        String director = "storage/paw-img";

        if (!optionalFile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                File file = new File(director + "/" + id + ".bin");
                long fileSize = file.length();

                byte[] content = new byte[(int) fileSize];

                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    int bytesRead = fileInputStream.read(content);

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_PNG); // Set the appropriate media type for your image

                    return new ResponseEntity<>(content, headers, HttpStatus.OK);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
