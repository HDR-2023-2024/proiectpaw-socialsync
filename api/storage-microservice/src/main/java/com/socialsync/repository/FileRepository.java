package com.socialsync.repository;

import com.socialsync.pojo.FileInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface FileRepository extends JpaRepository<FileInfo,String> {
    @Modifying
    @Transactional
    @Query("UPDATE FileInfo f SET f.isConfirmed = true WHERE f.id = :fileId")
    void setConfirmedForFileId(String fileId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FileInfo f WHERE f.isConfirmed = false AND f.dateCreated < :cutoffDate")
    void deleteUnconfirmedFilesOlderThan5Minutes(LocalDateTime cutoffDate);
}
