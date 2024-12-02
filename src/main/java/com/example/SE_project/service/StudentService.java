package com.example.SE_project.service;

import com.example.SE_project.dto.*;
import com.example.SE_project.entity.File;
import com.example.SE_project.entity.Student;
import com.example.SE_project.entity.key.PrintKey;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    List<StudentDTO> allStudents();

    StudentDTO getInfo(String id);

    List<File> getFiles(String id);

    List<requestDTO> getHistory(String id);

    StatisticDTO getStatistic(String id,Integer year);

    File addNewFile(String id, NewFileDTO newFileDTO , MultipartFile f );
    String deleteFile(String file_id);

    requestDTO sendPrintRequest(SendRequestDTO sendRequestDTO,String id, Integer start_page, Integer end_page, List<Integer> list_page);

    String deletePrintRequest(PrintKey printKey);

    List<printerDTO> getAllAvailablePrinter();

}