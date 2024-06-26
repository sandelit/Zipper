package com.file.zipper.service;

import com.file.zipper.entity.UploadStatistics;
import com.file.zipper.repository.UploadStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UploadStatisticsService {
    @Autowired
    private UploadStatisticsRepository statisticsRepository;

    public void updateStatistics(String ipAddress) {
        LocalDate today = LocalDate.now();
        UploadStatistics statistics = statisticsRepository.findByIpAddressAndDate(ipAddress, today);

        if (statistics == null) {
            statistics = new UploadStatistics();
            statistics.setIpAddress(ipAddress);
            statistics.setDate(today);
            statistics.setUsageCount(1);
        } else {
            statistics.setUsageCount(statistics.getUsageCount() + 1);
        }

        statisticsRepository.save(statistics);
    }
}
