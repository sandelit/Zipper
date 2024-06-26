package com.file.zipper.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileZipperService {

    public byte[] zipFiles(List<MultipartFile> files) throws IOException {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutStream = new ZipOutputStream(byteOutStream)) {
            for (MultipartFile file : files) {
                ZipEntry entry = new ZipEntry(Objects.requireNonNull(file.getOriginalFilename()));
                entry.setSize(file.getSize());
                zipOutStream.putNextEntry(entry);
                zipOutStream.write(file.getBytes());
                zipOutStream.closeEntry();
            }
        }
        return byteOutStream.toByteArray();
    }
}
