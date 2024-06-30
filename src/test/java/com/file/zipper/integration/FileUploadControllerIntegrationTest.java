package com.file.zipper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;

import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testUploadFiles() {
        String url = "http://localhost:" + port + "/api/zipper";
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("files", "test content".getBytes()).header("Content-Disposition", "form-data; name=files; filename=test1.txt");
        builder.part("files", "test content".getBytes()).header("Content-Disposition", "form-data; name=files; filename=test2.txt");

        MultiValueMap<String, HttpEntity<?>> multipartRequest = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(multipartRequest, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUploadLargeFile() {
        String url = "http://localhost:" + port + "/api/zipper";
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        byte[] largeFileContent = new byte[1024 * 1024 + 1];
        builder.part("files", largeFileContent).header("Content-Disposition", "form-data; name=files; filename=largeFile.txt");

        MultiValueMap<String, HttpEntity<?>> multipartRequest = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(multipartRequest, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, response.getStatusCode());
    }
}
