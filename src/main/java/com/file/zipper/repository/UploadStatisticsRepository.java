package com.file.zipper.repository;

import com.file.zipper.entity.UploadStatistics;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface UploadStatisticsRepository extends CrudRepository<UploadStatistics, Long> {
    UploadStatistics findByIpAddressAndDate(String ipAddress, LocalDate date);
}
