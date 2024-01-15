package com.socialsync.notifymicroservice.service;

import com.socialsync.notifymicroservice.dto.ReportDTO;
import com.socialsync.notifymicroservice.pojo.PersistentPost;
import com.socialsync.notifymicroservice.pojo.Post;
import com.socialsync.notifymicroservice.pojo.PostQueueMessage;
import com.socialsync.notifymicroservice.pojo.Report;
import com.socialsync.notifymicroservice.repositories.PersistentPostRepository;
import com.socialsync.notifymicroservice.repositories.PostRepository;
import com.socialsync.notifymicroservice.repositories.ReportRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ReportService {
    private ReportRepository reportRepository;

    public List<Report> getAllReports () {
        List<Report> reportList = reportRepository.findAll();
        return reportList;
    }

    // Delete la reporturile vazute
    public void deleteById(String report_id) {
        reportRepository.deleteById(report_id);
    }

    // slavare
    public boolean saveReport(ReportDTO reportDTO){
        // verficare sa nu fi dat deja report
        Optional<Report> rep = reportRepository.findAllByPostIdAndUserId(reportDTO.getPostId(), reportDTO.getUserId());
        if(rep.isPresent())
            return false;

        Report report = new Report();
        report.setPostTitle(reportDTO.getPostTitle());
        report.setUsername(reportDTO.getUsername());
        report.setPostId(reportDTO.getPostId());
        report.setUserId(reportDTO.getUserId());
        reportRepository.save(report);
        return  true;
    }
}
