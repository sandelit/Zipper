package com.file.zipper.controller;

import com.file.zipper.service.FileZipperService;
import com.file.zipper.service.UploadStatisticsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FileZipperService zipperService;

    @Autowired
    private UploadStatisticsService statisticsService;

    @PostMapping("/zipper")
    public ResponseEntity<byte[] > uploadFiles(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request ) {
        for (MultipartFile file : files) {
            if (file.getSize() > 1024 * 1024) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        String ipAddress = request.getRemoteAddr();
        try {
            byte[] zippedBytes = zipperService.zipFiles(files);
            statisticsService.updateStatistics(ipAddress);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=files.zip");

            return new ResponseEntity<>(zippedBytes, headers, HttpStatus.OK);
        }
        catch (IOException e) {
            System.out.println("Failed to zip files");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}