package com.example.SE_project.service.service_impl;

import com.example.SE_project.dto.adminDTO;
import com.example.SE_project.dto.printerDTO;
import com.example.SE_project.dto.requestDTO;
import com.example.SE_project.entity.Admin;
import com.example.SE_project.entity.Print;
import com.example.SE_project.entity.Printer;
import com.example.SE_project.entity.*;
import com.example.SE_project.entity.key.PrintKey;
import com.example.SE_project.reposistory.*;
import com.example.SE_project.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@AllArgsConstructor
public class adminServiceImpl implements AdminService {

    private AdminRepository adminRepository ;
    private StudentRepository studentRepository;
    private PrinterRepository printerRepository ;
    //private SPSSRepository SPSSRepository;
    private PrintRepository printRepository ;
    private FileRepository fileRepository;
    private ModelMapper modelMapper;


    public adminDTO  find_admin_information(String id){
        Admin admin = adminRepository.findAdminById(id);
        return modelMapper.map(admin , adminDTO.class);
    }

    public List<printerDTO> get_all_printer() {
        return printerRepository.findAll().stream()
                .map(printer -> modelMapper.map(printer, printerDTO.class))
                .collect(Collectors.toList());
    }


    public List<requestDTO>  mapping_requestDTO(List<Object[]> res ){
        List<requestDTO> requestDetails = new ArrayList<>();
        for (Object[] row : res ){
            String id = row[0] != null ? row[0].toString() : null;
            String fileName = row[1] != null ? row[1].toString() : null;
            Integer nbOfPageUsed = row[2] != null ? ((Number) row[2]).intValue() : null;
            Integer status = row[3] != null ? ((Number) row[3]).intValue() : null;
            String printDate = row[4] != null ? row[4].toString() : null;
            String building = row[5] != null ? row[5].toString() : null ;
            String printer_id = row[6] != null ? row[6].toString() : null ;
            requestDTO dto = new requestDTO(id,fileName,nbOfPageUsed,status,printDate , building , printer_id);
            requestDetails.add(dto);
        }
        return requestDetails;
    }


    public List<requestDTO> get_all_print_request() {
        List<Object[]> results =adminRepository.getStudentPrintDetails();
        return mapping_requestDTO(results);
    }

    public List<requestDTO> get_all_print_request_by_printer_id(String id){
        List<Object[]> res = adminRepository.GetStudentPrintDetailsByPrinterId(id);
        return mapping_requestDTO(res);
    }

    public List<requestDTO> get_all_print_request_by_student_id(String id){
        List<Object[]> res = adminRepository.GetStudentPrintDetailsByStudentId(id);
        return mapping_requestDTO(res);
    }

    public Printer insertNewPrinter(String building, String model, Date date) {
        List<Object[]> result = adminRepository.insertNewPrinter(building, model, date);
        if (result != null && !result.isEmpty()) {
            Object[] row = result.get(0);
            Printer printer = new Printer();
            printer.setPrinter_id((String) row[0]);
            printer.setBuilding((String) row[1]);
            printer.setState((Integer) row[2]);
            printer.setModel((String) row[3]);
            printer.setImport_date((java.sql.Date) row[4]);
            return printer;
        }
        return null;
    }

//    public SPSS set_Num_Paper_Left() {
//
//        return null;
//    }
//
//
//    public SPSS set_Num_Paper_Default(String semester, int num) {
//        SPSS temp = SPSSRepository.findBySemester(semester);
//        temp.setNb_of_page_default(num);
//        SPSSRepository.save(temp);
//        return temp;
//    }
    public List<Integer> generateSemesterCodeBaseOnRealTime() {
        // Get current year

        Integer year = java.time.LocalDate.now().getYear();

        // Get current month
        Integer month = java.time.LocalDate.now().getMonthValue();

        // Get current semester
        Integer semesterCode;
        if (month >= 1 && month <= 5) {
            semesterCode = 2;
        } else if (month >= 9 && month <= 12) {
            semesterCode = 1;
        } else {
            semesterCode = 3;
        }
        Integer semesterYear;
        if(semesterCode == 1) semesterYear = year - 2000;
        else semesterYear = year - 2001;

        List<Integer> generateSemesterCode= new ArrayList<>();
        generateSemesterCode.add(semesterYear);
        generateSemesterCode.add(semesterCode);

        return generateSemesterCode;
    }


    public SPSS reset(String semester, Integer num) {
        List<Integer> a = new ArrayList<>();
        a.add(Integer.parseInt(semester.substring(2,4)));
        a.add(Integer.parseInt(semester.substring(4,5)));
        if(generateSemesterCodeBaseOnRealTime().get(0)<a.get(0))
            return null; else
                if(generateSemesterCodeBaseOnRealTime().get(0).equals(a.get(0))
                      && generateSemesterCodeBaseOnRealTime().get(1)<a.get(1))  return null;
        Date sqlDate = getCurrentSqlDate();
                SPSS newsemester = new SPSS(semester,num,0, sqlDate);
        List<Student> allstudents = studentRepository.findAll();
        for(Student student:allstudents){
            student.setNb_of_page_left(student.getNb_of_page_left()+num);
            studentRepository.save(student);
        }

                return newsemester;
    }
    public static Date getCurrentSqlDate() {
        long currentTimeMillis = System.currentTimeMillis();
        return new Date(currentTimeMillis); // Tạo đối tượng java.sql.Date từ thời gian hiện tại
    }

    public printerDTO updateStatePrinter (String printer_id){
        printerDTO printer = new printerDTO();
        List<Object[]>  res = printerRepository.UpdatePrinterState(printer_id);
        Object[] row = res.get(0);
        printer.setPrinter_id((String) row[0]);
        printer.setBuilding((String) row[1]);
        printer.setState((Integer) row[2]);
        printer.setModel((String) row[3]);
        printer.setImport_date((java.sql.Date) row[4]);
        return printer;
    }

    public Print refusePrint(String printer_id , String file_id , Integer status){
        List<Object[]> res = printRepository.updatePrintStatus(printer_id, file_id , status);
        Object[] row = res.get(0);
        Print  pr = new Print();
        PrintKey pk = new PrintKey((String) row[0],(String) row[1]);
        pr.setPrintKey(pk);
        pr.setNb_of_page_used((Integer) row[2]);
        pr.setNb_of_copy((Integer) row[3]);
        pr.setPaper_size((String) row [4]);
        pr.setStatus((Integer) row[5]);
        pr.setOne_or_two_side((Integer)  row[6] );
        pr.setPrint_date((java.sql.Date) row[7]);
        return pr;
    }
    public Print acceptPrint(String printer_id , String file_id ){
        Print print = printRepository.findByPrintKey_FileIdAndPrintKey_PrinterId(file_id,printer_id);
        File file = fileRepository.findFileByFileid(file_id);
        Student student= file.getStudent();
        student.setNb_of_page_left(student.getNb_of_page_left()- file.getNum_pages());
        studentRepository.save(student);
        print.setStatus(2);
        Date sqlDate = getCurrentSqlDate();
        print.setPrint_date(sqlDate);
        printRepository.save(print);
        return print;
    }


}
