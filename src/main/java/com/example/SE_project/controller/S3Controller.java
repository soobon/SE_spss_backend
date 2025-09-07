package com.example.SE_project.controller;

import com.example.SE_project.service.IS3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class S3Controller {

    private IS3Service s3Service;

    @GetMapping
    public ResponseEntity<?> getAllBuckets(){
        return ResponseEntity.ok(s3Service.listBuckets().size());
    }

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        try {
            s3Service.uploadFile(file, "A", "B");
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("File upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/1")
    public ResponseEntity<?> multipartUploadFileSequentially(@RequestParam("file") MultipartFile file){
        try {
            String timer = s3Service.multipartUploadFileSequentially(file, "A", "B");
            return ResponseEntity.ok("File uploaded successfully in " + timer);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("File upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/2")
    public ResponseEntity<?> multipartUploadFileConcurrently(@RequestParam("file") MultipartFile file){
        try {
            String timer = s3Service.multipartUploadFileConcurrently(file, "A", "B");
            return ResponseEntity.ok("File uploaded successfully in " + timer);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("File upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFile(
            @RequestParam("fileId") String fileId,
            @RequestParam("studentId") String studentId
    ){
        try {
            s3Service.deleteFile(fileId, studentId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("File deletion failed: " + e.getMessage());
        }
    }
}
