package com.example.SE_project.service.service_impl;

import com.example.SE_project.dto.FileHistoryDTO;
import com.example.SE_project.dto.PrintDTO;
import com.example.SE_project.dto.StatisticDTO;
import com.example.SE_project.dto.StudentDTO;
import com.example.SE_project.entity.File;
import com.example.SE_project.entity.Student;
import com.example.SE_project.exception.UserNotFound;
import com.example.SE_project.reposistory.PrintRepository;
import com.example.SE_project.reposistory.StudentRepository;
import com.example.SE_project.service.StudentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private PrintRepository printRepository;

    private ModelMapper modelMapper;

    @Override
    public List<StudentDTO> allStudents() {
        return studentRepository.findAll().stream()
                .map(p -> modelMapper.map(p,StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getInfo(String id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new UserNotFound("Can't find Student by ID: " + id)
        );
        return modelMapper.map(student,StudentDTO.class);
    }

    @Override
    public List<File> getFiles(String id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new UserNotFound("Can't find Student by ID: " + id)
        );
        return student.getFile_list();
    }

    @Override
    public List<FileHistoryDTO> getHistory(String id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new UserNotFound("Can't find Student by ID: " + id)
        );
        List<FileHistoryDTO> fileHistoryDTOS = new ArrayList<>();
        List<File> fileList = student.getFile_list();

        fileList.stream()
                .map(p -> fileHistoryDTOS.add(
                        new FileHistoryDTO(
                                p.getFile_name()
                                ,p.getNum_pages()
                                ,p.getUpload_date()
                                ,p.getPrint_list().stream().map(print -> modelMapper.map(print, PrintDTO.class)).collect(Collectors.toList())
                        )
                ))
                .collect(Collectors.toList());
        return fileHistoryDTOS;
    }

    @Override
    public StatisticDTO getStatistic(String id, Integer month) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new UserNotFound("Can't find Student by ID: " + id)
        );

        return new StatisticDTO(
                student.getNb_of_page_left(),
                printRepository.findTotalPagesUsedByStudent(id),
                student.getFile_list().stream().mapToInt(p -> p.getPrint_list().size()).sum(),
                printRepository.findPrintCountForSpecificMonth(id, month)
        );
    }
}
