package com.file.zipper.controller;

import com.file.zipper.service.FileZipperService;
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
    private FileZipperService fileZipperService;

    @PostMapping("/zipper")
    public ResponseEntity<byte[]> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {

        byte[] zippedBytes = fileZipperService.zipFiles(files);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=files.zip");

        return new ResponseEntity<>(zippedBytes, headers, HttpStatus.OK);

    }
}