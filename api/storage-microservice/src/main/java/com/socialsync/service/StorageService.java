package com.socialsync.service;

import com.google.gson.Gson;
import com.socialsync.pojo.PhotoMessageDto;
import com.socialsync.repository.FileRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.socialsync.pojo.FileInfo;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StorageService {
    @Autowired
    private FileRepository fileInfoRepository;
    @Autowired
    private Gson gson;

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.users}")
    void receiveQueueMessageUsers(String msg) {
        String msgQ = gson.fromJson(msg, String.class);
        try {
            log.info(msgQ);
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.posts}")
    void receiveQueueMessagePost(String msg) {
        PhotoMessageDto msgQ = gson.fromJson(msg, PhotoMessageDto.class);

        try {
            //log.info(msgQ.getOperation() + " " + msgQ.getUrl());
            if(Objects.equals(msgQ.getOperation(), "Delete")){
                this.fileInfoRepository.deleteById(msgQ.getUrl().replace("http://localhost:8088/api/v1/storage/img/",""));
            }
            if(Objects.equals(msgQ.getOperation(), "Insert")){
                this.fileInfoRepository.setConfirmedForFileId(msgQ.getUrl().replace("http://localhost:8088/api/v1/storage/img/",""));
            }
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    @RabbitListener(queues = "${socialsync.rabbitmq.queue.topics}")
    void receiveQueueMessageComment(String msg) {
        String msgQ = gson.fromJson(msg, String.class);

        try {
            log.info(msgQ);
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    public List<Map.Entry<String, String>> getFileNamesByIds(List<String> ids) {
        return this.fileInfoRepository.findAllById(ids).stream()
                .map(fileInfo -> new AbstractMap.SimpleEntry<>(fileInfo.getId(), fileInfo.getFilename()))
                .collect(Collectors.toList());
    }

    public Optional<FileInfo> findById(String id) {
        return this.fileInfoRepository.findById(id);
    }

    public FileInfo save(FileInfo file){
        LocalDate date = LocalDate.now();
        return   fileInfoRepository.save(new FileInfo(null,file.getFilename(),false,date,file.getContent()));
    }
}
