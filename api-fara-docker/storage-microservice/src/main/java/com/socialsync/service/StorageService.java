package com.socialsync.service;

import com.socialsync.repository.FileRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import com.socialsync.pojo.FileInfo;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StorageService {
    @Autowired
    private FileRepository fileInfoRepository;

    public List<Map.Entry<String, String>> getFileNamesByIds(List<String> ids) {
        return this.fileInfoRepository.findAllById(ids).stream()
                .map(fileInfo -> new AbstractMap.SimpleEntry<>(fileInfo.getId(), fileInfo.getFilename()))
                .collect(Collectors.toList());
    }
}
