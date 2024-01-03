package com.socialsync.api;

import com.google.gson.Gson;
import com.socialsync.pojo.*;
import com.socialsync.repository.FileRepository;
import com.socialsync.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
@RequestMapping("api/v1/storage")
@AllArgsConstructor
@Slf4j
public class StorageController {
    private StorageService storageService;

    @GetMapping("/img/{id}")
    public ResponseEntity<byte[]> getMemoryFileByIdImg(@PathVariable("id") String id) {
        Optional<FileInfo> optionalFile = this.storageService.findById(id);
        if (!optionalFile.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(optionalFile.get().getContent(), headers, HttpStatus.OK);
        }
    }

    @PostMapping("/upload-multipartFile")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        // se trece prin fiecare fisier
        List<UrlDto> url = new ArrayList<>();

        if (files.size() < 1) {
            return new ResponseEntity<>(new ErrorClass("Nu sa trimis nici o imagine pentru a fi salvata!"),HttpStatus.NOT_ACCEPTABLE);
        }
        for (MultipartFile file : files) {
            if (isImageFile(file.getOriginalFilename())) {
                try {
                    FileInfo fileInfo = storageService.save(new FileInfo(file.getOriginalFilename(), file.getBytes()));
                    System.out.println(file.getOriginalFilename());
                    url.add(new UrlDto("http://localhost:8088/api/v1/storage/img/" + fileInfo.getId()));
                } catch (IOException ioException) {
                    System.out.print(ioException.getMessage());
                    return new ResponseEntity<>(new ErrorClass("Eroare la salvarea fisierelor."),HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                return new ResponseEntity<>(new ErrorClass("Formatul fisierului nu este valid."),HttpStatus.NOT_ACCEPTABLE);
            }
        }

        return new ResponseEntity(url, HttpStatus.OK);
    }

    @PostMapping("/upload-json")
    public ResponseEntity<?> uploadFilesJSON(@RequestBody List<JsonFile> files) {
        List<UrlDto> urls = new ArrayList<>();

        if (files.isEmpty()) {
            return new ResponseEntity<>(new ErrorClass("Nu sa trimis nici o imagine pentru a fi salvata!"),HttpStatus.NOT_ACCEPTABLE);
        }

        for (JsonFile file : files) {
            // daca e imagine
            if (isImageFile(file.getFileName())) {
                FileInfo fileInfo = storageService.save(new FileInfo(file.getFileName(), file.getContent()));
                urls.add(new UrlDto("http://localhost:8088/api/v1/storage/img/" + fileInfo.getId()));
            }else{
                return new ResponseEntity<>(new ErrorClass("Formatul fisierului nu este valid."),HttpStatus.NOT_ACCEPTABLE);
            }
        }

        return new ResponseEntity<>(urls, HttpStatus.OK);
    }

    private boolean isImageFile(String fileName) {
        return fileName != null && (fileName.toLowerCase().endsWith(".png") ||
                fileName.toLowerCase().endsWith(".jpg") ||
                fileName.toLowerCase().endsWith(".jpeg") ||
                fileName.toLowerCase().endsWith(".gif"));
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



    @PostMapping("/get-file-names")
    public List<Map.Entry<String, String>> getFileNamesByIds(@RequestBody ListUrl listUrl) {
        return storageService.getFileNamesByIds(listUrl.getElements());
    }
}
