package com.socialsync.repository;

import com.socialsync.pojo.FileInfoSQL;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FileRepository extends JpaRepository<FileInfoSQL,String> {
    @Modifying
    @Transactional
    @Query("UPDATE FileInfoSQL f SET f.isConfirmed = true WHERE f.id = :fileId")
    void setConfirmedForFileId(String fileId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FileInfoSQL f WHERE f.isConfirmed = false AND f.dateCreated < :cutoffDate")
    void deleteUnconfirmedFilesOlderThan5Minutes(LocalDateTime cutoffDate);
}
