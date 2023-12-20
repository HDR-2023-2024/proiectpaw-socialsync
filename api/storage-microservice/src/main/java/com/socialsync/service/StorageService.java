package com.socialsync.service;

import com.socialsync.repository.FileRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import com.socialsync.pojo.FileInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {
    @Autowired
    private FileRepository fileInfoRepository;

    public List<String> getFileNamesByIds(List<String> ids) {
        return this.fileInfoRepository.findAllById(ids).stream()
                .map(FileInfo::getFilename)
                .toList();
    }
}
