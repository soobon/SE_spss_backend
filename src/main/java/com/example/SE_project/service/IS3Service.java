package com.example.SE_project.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;

public interface IS3Service {
    List<Bucket> listBuckets();
    String uploadFile(MultipartFile file, String file_id, String studentId);
    String multipartUploadFileSequentially(MultipartFile file, String file_id, String studentId);
    String multipartUploadFileConcurrently(MultipartFile file, String file_id, String studentId);
    String deleteFile(String file_id, String studentId);
}
