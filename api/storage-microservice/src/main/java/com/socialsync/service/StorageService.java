package com.socialsync.service;

import com.google.gson.Gson;
import com.socialsync.pojo.FileInfoDTO;
import com.socialsync.pojo.PhotoMessageDto;
import com.socialsync.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.socialsync.pojo.FileInfoSQL;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@Slf4j
public class StorageService {
    @Autowired
    private FileRepository fileInfoRepository;
    @Autowired
    private Gson gson;
    @Autowired
    private MinIoService minIoService;

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

    public FileInfoDTO findById(String id) {
        Optional<FileInfoSQL> fileInfo = fileInfoRepository.findById(id);
        if(fileInfo.isPresent()) {
            byte[] content = this.minIoService.readFromMinIO(fileInfo.get().getId() + ".bin");
            return new FileInfoDTO(fileInfo.get().getId(),fileInfo.get().getFilename(),fileInfo.get().isConfirmed(),fileInfo.get().getDateCreated(),content);
        }
        return null;
    }

    public FileInfoSQL save(FileInfoDTO file){
        LocalDateTime date = LocalDateTime.now();
        FileInfoSQL fileInfo =  fileInfoRepository.save(new FileInfoSQL(null,file.getFilename(),false,date));
        this.minIoService.writeToMinIO(fileInfo.getId() + ".bin",file.getContent());
        return  fileInfo;
    }

    @Scheduled(fixedRate = 36000000) // 36000000 milisecunde = 10 ore
    public void myScheduledMethod() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime cutoffDateTime = currentDateTime.minusHours(10);

        fileInfoRepository.deleteUnconfirmedFilesOlderThan5Minutes(cutoffDateTime);
        log.info("Stergere fisiere neconfirmate mai vechi de 10 ore.");
    }
}
