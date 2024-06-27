package com.file.zipper.unit;

import com.file.zipper.entity.UploadStatistics;
import com.file.zipper.repository.UploadStatisticsRepository;
import com.file.zipper.service.UploadStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UploadStatisticsServiceTest {

    @Mock
    private UploadStatisticsRepository statisticsRepository;

    @InjectMocks
    private UploadStatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewStatisticsEntry() {
        String ipAddress = "127.0.0.1";
        LocalDate today = LocalDate.now();

        when(statisticsRepository.findByIpAddressAndDate(ipAddress, today)).thenReturn(null);

        statisticsService.updateStatistics(ipAddress);

        verify(statisticsRepository, times(1)).save(any(UploadStatistics.class));
    }

    @Test
    void testUpdateStatisticsEntry() {
        String ipAddress = "127.0.0.1";
        LocalDate today = LocalDate.now();
        UploadStatistics existingStatistics = new UploadStatistics();
        existingStatistics.setIpAddress(ipAddress);
        existingStatistics.setDate(today);
        existingStatistics.setUsageCount(1);

        when(statisticsRepository.findByIpAddressAndDate(ipAddress, today)).thenReturn(existingStatistics);

        statisticsService.updateStatistics(ipAddress);

        verify(statisticsRepository, times(1)).save(existingStatistics);
        assertEquals(2, existingStatistics.getUsageCount());
    }
}
