package com.example.SE_project.service.service_impl;

import com.example.SE_project.dto.*;
import com.example.SE_project.entity.File;
import com.example.SE_project.entity.Print;
import com.example.SE_project.entity.Printer;
import com.example.SE_project.entity.Student;
import com.example.SE_project.entity.key.PrintKey;
import com.example.SE_project.exception.UserNotFound;
import com.example.SE_project.reposistory.FileRepository;
import com.example.SE_project.reposistory.PrintRepository;
import com.example.SE_project.reposistory.PrinterRepository;
import com.example.SE_project.reposistory.StudentRepository;
import com.example.SE_project.service.StudentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private PrintRepository printRepository;

    private ModelMapper modelMapper;

    private PrinterRepository printerRepository;

    private FileRepository fileRepository;

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
    public List<requestDTO> getHistory(String id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new UserNotFound("Can't find Student by ID: " + id)
        );
        List<requestDTO> requestDTOS = new ArrayList<>();
        List<File> fileList = student.getFile_list();

        for (File file : fileList){
            List<Print> printList = new ArrayList<>();
            List<Print> prints = printRepository.findAllByFile_Fileid(file.getFileid());
            for (Print print : prints){
                printList.add(print);
            }
            printList.forEach( p ->
                    requestDTOS.add(
                            requestDTO.builder()
                                    .id(id)
                                    .file_name(file.getFile_name())
                                    .nb_of_page_used(p.getNb_of_page_used())
                                    .statuss(p.getStatus())
                                    .print_date(p.getPrint_date().toString())
                                    .building(printerRepository.findById(p.getPrinter().getPrinter_id()).get().getBuilding())
                                    .print_id(p.getPrinter().getPrinter_id())
                                    .file_id(file.getFileid())
                                    .order_num(p.getPrintKey().getOrderNum())
                                    .build()
                    )
            );
        }
        return requestDTOS;
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

    @Override
    public File addNewFile(String id, NewFileDTO newFileDTO) {
        //generate ID for file
        StringBuilder file_id = null;
        while (true) {
            file_id = new StringBuilder("F");
            Random random = new Random();
            for (int i=0; i<10; i++){
                file_id.append(random.nextInt(10));
            }
            if (fileRepository.findById(file_id.toString()).isEmpty()) break;
        }
        //now
        LocalDate localDate = LocalDate.now();
        //java.sql.Date
        Date sqlDate = Date.valueOf(localDate);

        Student student = studentRepository.findById(id).orElseThrow(
                () -> new UserNotFound("Can't find Student by ID: " + id)
        );

        File file = File.builder()
                .fileid(file_id.toString())
                .upload_date(sqlDate)
                .file_name(newFileDTO.getFile_name())
                .num_pages(newFileDTO.getNum_pages())
                .student(student)
                .build();

        return fileRepository.save(file);
    }

    @Override
    public String deleteFile(String file_id){
        File file = fileRepository.findById(file_id).orElseThrow(
                () -> new UserNotFound("File not found by Id:" + file_id)
        );
        fileRepository.deleteById(file_id);
        return "File deleted successfully";
    }

    @Override
    public requestDTO sendPrintRequest(SendRequestDTO sendRequestDTO, String id) {
        File file = fileRepository.findById(sendRequestDTO.getFile_id())
                        .orElseThrow(
                () -> new UserNotFound("File not found by Id:" + sendRequestDTO.getFile_id())
        );

        PrintKey printKey = new PrintKey(0, file.getFileid());

        //now
        LocalDate localDate = LocalDate.now();
        //java.sql.Date
        Date sqlDate = Date.valueOf(localDate);

        Integer nb_of_page_in_print = file.getNum_pages();

        if (sendRequestDTO.getPaper_size().equals("A3")) nb_of_page_in_print *= 2;

        if (sendRequestDTO.getOne_or_two_side().equals(2)) nb_of_page_in_print *= 2;

        nb_of_page_in_print *= sendRequestDTO.getNb_of_copy();

        Printer printer = printerRepository.findById(sendRequestDTO.getPrinter_id()).orElseThrow(
                () -> new UserNotFound("Printer not found by Id:" + sendRequestDTO.getPrinter_id())
        );

        Student student = studentRepository.findById(id).orElseThrow(
                () -> new UserNotFound("Can't find Student by ID: " + id)
        );

        if (nb_of_page_in_print > student.getNb_of_page_left()){
            return null;
        }

        Print print = Print.builder()
                .printKey(printKey)
                .file(file)
                .printer(printer)
                .nb_of_copy(sendRequestDTO.getNb_of_copy())
                .paper_size(sendRequestDTO.getPaper_size())
                .status(0)
                .one_or_two_side(sendRequestDTO.getOne_or_two_side())
                .print_date(sqlDate)
                .nb_of_page_used(nb_of_page_in_print)
                .build();
        printRepository.save(print);

        List<Object[]> newPrints = printRepository.updateOrderNum();
        Object[] newPrint = newPrints.get(0);

        return requestDTO.builder()
                .id(id)
                .file_name(file.getFile_name())
                .nb_of_page_used(print.getNb_of_page_used())
                .statuss(0)
                .print_date(print.getPrint_date().toString())
                .building(printer.getBuilding())
                .print_id(printer.getPrinter_id())
                .file_id(file.getFileid())
                .order_num((Integer) newPrint[8])
                .build();
    }
}
