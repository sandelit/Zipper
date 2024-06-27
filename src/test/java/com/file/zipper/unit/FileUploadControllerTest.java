package com.file.zipper.unit;

import com.file.zipper.controller.FileUploadController;
import com.file.zipper.service.FileZipperService;
import com.file.zipper.service.UploadStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileUploadControllerTest {

    @Mock
    private FileZipperService zipperService;

    @Mock
    private UploadStatisticsService statisticsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFilesOk() throws IOException {
        MockMultipartFile file1 = new MockMultipartFile("file", "test1.txt", "text/plain", "test content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file", "test2.txt", "text/plain", "test content".getBytes());

        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(zipperService.zipFiles(anyList())).thenReturn(new byte[0]);

        ResponseEntity<byte[]> response = fileUploadController.uploadFiles(List.of(file1, file2), request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(statisticsService, times(1)).updateStatistics("127.0.0.1");
    }

    @Test
    void testUploadFilesFail() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test1.txt", "text/plain", "test content".getBytes());

        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(zipperService.zipFiles(anyList())).thenThrow(new IOException());

        ResponseEntity<byte[]> response = fileUploadController.uploadFiles(List.of(file), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(statisticsService, never()).updateStatistics(anyString());
    }

    @Test
    void testUploadTooLargeFile() {
        byte[] largeFileContent = new byte[1024 * 1024 + 1];
        MockMultipartFile largeFile = new MockMultipartFile("file", "largeFile.txt", "text/plain", largeFileContent);

        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        ResponseEntity<byte[]> response = fileUploadController.uploadFiles(List.of(largeFile), request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(statisticsService, never()).updateStatistics(anyString());
    }
}
