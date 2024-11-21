package com.example.SE_project.service;

import com.example.SE_project.dto.FileHistoryDTO;
import com.example.SE_project.dto.StatisticDTO;
import com.example.SE_project.dto.StudentDTO;
import com.example.SE_project.entity.File;
import com.example.SE_project.entity.Student;

import java.util.List;

public interface StudentService {

    List<StudentDTO> allStudents();

    StudentDTO getInfo(String id);

    List<File> getFiles(String id);

    List<FileHistoryDTO> getHistory(String id);

    StatisticDTO getStatistic(String id,Integer month);

}