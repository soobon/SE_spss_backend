package com.example.SE_project.service;

import com.example.SE_project.entity.storage;
import com.example.SE_project.reposistory.storageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class storageService {
    @Autowired
    private storageRepository storageRepository ;

    public String uploadFile(MultipartFile file, String id) throws IOException {
        byte[] compressedData = fileUtils.compressImage(file.getBytes());
        System.out.println("Original size: " + file.getSize() + " bytes");
        System.out.println("Compressed size: " + compressedData.length + " bytes");

        storage filedata = storageRepository.save(storage.builder()
                .fileid(id)
                .filedata(compressedData)
                .build());

        if (filedata != null) {
            return "File uploaded successfully: " + id;
        } else {
            return "File upload failed: " + id;
        }
    }


    public byte[] downloadFile (String fileid) {
        Optional<storage> filedata = storageRepository.findById(fileid);
        byte[] file = fileUtils.decompressImage(filedata.get().getFiledata());
        return file;
    }

}
