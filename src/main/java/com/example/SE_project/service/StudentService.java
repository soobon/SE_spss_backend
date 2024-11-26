package com.example.SE_project.service;

import com.example.SE_project.dto.*;
import com.example.SE_project.entity.File;
import com.example.SE_project.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    List<StudentDTO> allStudents();

    StudentDTO getInfo(String id);

    List<File> getFiles(String id);

    List<requestDTO> getHistory(String id);

    StatisticDTO getStatistic(String id,Integer month);

    File addNewFile(String id, NewFileDTO newFileDTO , MultipartFile f );
    String deleteFile(String file_id);

    requestDTO sendPrintRequest(SendRequestDTO sendRequestDTO,String id);

}