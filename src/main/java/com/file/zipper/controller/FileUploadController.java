package com.file.zipper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @PostMapping("/zipper")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        for (MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
        }
        return new ResponseEntity<>("test", HttpStatus.OK);

    }
}