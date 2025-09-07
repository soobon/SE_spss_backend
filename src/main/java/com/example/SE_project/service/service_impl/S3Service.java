package com.example.SE_project.service.service_impl;

import com.example.SE_project.service.IS3Service;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class S3Service implements IS3Service {

    @Autowired
    private S3Client s3Client;

    @Value(value = "${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public List<Bucket> listBuckets() {
        //list all bucket
        List<Bucket> allBuckets = new ArrayList<>();
        String nextToken = null;

        do {
            String continuationToken = nextToken;
            ListBucketsResponse listBucketsResponse = s3Client.listBuckets(
                    request -> request.continuationToken(continuationToken)
            );

            allBuckets.addAll(listBucketsResponse.buckets());
            nextToken = listBucketsResponse.continuationToken();
        } while (nextToken != null);

        return allBuckets;
    }

    @Override
    public String uploadFile(MultipartFile file, String fileId,String studentId) {
        try {
            String keyName = studentId + "/" + fileId;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName) // need to modify
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return "File uploaded: " + keyName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String multipartUploadFileSequentially(MultipartFile multipartFile, String file_id, String studentId) {
        String uploadId = null;
        try {
            //convert MultipartFile to File
            File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);

            String keyName = studentId + "/" + file_id;

            //initial multipart upload request
            CreateMultipartUploadRequest createMultipartUploadRequest =
                    CreateMultipartUploadRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .contentType(multipartFile.getContentType())
                            .build();

            CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
            uploadId = response.uploadId();

            //separate file into multiple parts and upload in SEQUENCE
            int partSize = 8 * 1024 * 1024; // 8 MB
            int contentLength = (int) file.length();
            int partCount = (int) Math.ceil((double) contentLength / partSize);

            Long startTime = System.currentTimeMillis();

            List<CompletedPart> completedParts = new ArrayList<>();

            try (FileInputStream fis = new FileInputStream(file)) {
                for (int i=0; i<partCount; i++) {
                    int size = Math.min(partSize, contentLength - (i * partSize));
                    byte[] buffer = new byte[size];
                    int bytesRead = fis.read(buffer);

                    UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .uploadId(uploadId)
                            .partNumber(i + 1)
                            .contentLength((long) bytesRead)
                            .build();

                    UploadPartResponse uploadPartResponse = s3Client.uploadPart(
                            uploadPartRequest,
                            RequestBody.fromBytes(buffer)
                    );

                    completedParts.add(CompletedPart.builder()
                            .partNumber(i + 1)
                            .eTag(uploadPartResponse.eTag())
                            .build());
                }
            }

            Long duration = System.currentTimeMillis() - startTime;


            //done upload
            CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                    .parts(completedParts)
                    .build();

            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    CompleteMultipartUploadRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .uploadId(uploadId)
                            .multipartUpload(completedMultipartUpload)
                            .build();

            s3Client.completeMultipartUpload(completeMultipartUploadRequest);

            return duration.toString();
        } catch (Exception e) {
            //handle failed upload
            if (uploadId != null) {
                AbortMultipartUploadRequest abortMultipartUploadRequest = AbortMultipartUploadRequest.builder()
                        .bucket(bucketName)
                        .key(file_id)
                        .uploadId(uploadId)
                        .build();
                s3Client.abortMultipartUpload(abortMultipartUploadRequest);
            }
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public String multipartUploadFileConcurrently(MultipartFile multipartFile, String file_id, String studentId) {
        String uploadId = null;
        try {

            File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);

            String keyName = studentId + "/" + file_id;

            //initial multipart upload request
            CreateMultipartUploadRequest createMultipartUploadRequest =
                    CreateMultipartUploadRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .contentType(multipartFile.getContentType())
                            .build();

            CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
            uploadId = response.uploadId();
            final String currentUploadId = uploadId;

            //separate file into multiple parts and upload in PARALLEL
            long partSize = 8 * 1024 * 1024; // 8 MB
            long contentLength = (int) file.length();

            Long startTime = System.currentTimeMillis();

            List<CompletedPart> completedParts = Collections.synchronizedList(new ArrayList<>());

            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Future<?>> futures = new ArrayList<>();
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            try {
                int partNumber = 1;
                for (int filePosition = 0; filePosition < contentLength; filePosition += partSize) {
                    final int currentPartNumber = partNumber++;
                    final long start = filePosition;
                    final long size = Math.min(partSize, contentLength - start);

                    futures.add(executor.submit(() -> {
                        try {
                            byte[] buffer = new byte[(int) size];
                            synchronized (raf) {
                                raf.seek(start);
                                raf.readFully(buffer);
                            }

                            UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                                    .bucket(bucketName)
                                    .key(keyName)
                                    .uploadId(currentUploadId)
                                    .partNumber(currentPartNumber)
                                    .contentLength((long) buffer.length)
                                    .build();

                            UploadPartResponse uploadPartResponse = s3Client.uploadPart(
                                    uploadPartRequest,
                                    RequestBody.fromBytes(buffer)
                            );

                            completedParts.add(CompletedPart.builder()
                                    .partNumber(currentPartNumber)
                                    .eTag(uploadPartResponse.eTag())
                                    .build());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }));
                }

                for (Future<?> future : futures) {
                    future.get();
                }
            } finally {
                raf.close();
                executor.shutdown();
            }

            Long duration = System.currentTimeMillis() - startTime;

            //done upload
            CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                    .parts(completedParts.stream()
                            .sorted(Comparator.comparingInt(CompletedPart::partNumber))
                            .toList())
                    .build();

            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    CompleteMultipartUploadRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .uploadId(uploadId)
                            .multipartUpload(completedMultipartUpload)
                            .build();

            s3Client.completeMultipartUpload(completeMultipartUploadRequest);

            return duration.toString();
        } catch (Exception e) {
            //handle failed upload
            if (uploadId != null) {
                AbortMultipartUploadRequest abortMultipartUploadRequest = AbortMultipartUploadRequest.builder()
                        .bucket(bucketName)
                        .key(file_id)
                        .uploadId(uploadId)
                        .build();
                s3Client.abortMultipartUpload(abortMultipartUploadRequest);
            }
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public String deleteFile(String file_id, String studentId) {
        try {
            String keyName = studentId + "/" + file_id;

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

            return "File deleted: " + keyName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
