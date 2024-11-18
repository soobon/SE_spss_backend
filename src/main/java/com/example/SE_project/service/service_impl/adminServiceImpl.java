package com.example.SE_project.service.service_impl;

import com.example.SE_project.dto.adminDTO;
import com.example.SE_project.dto.printerDTO;
import com.example.SE_project.dto.requestDTO;
import com.example.SE_project.entity.Admin;
import com.example.SE_project.entity.Printer;
import com.example.SE_project.reposistory.AdminRepository;
import com.example.SE_project.reposistory.PrinterRepository;
import com.example.SE_project.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@AllArgsConstructor
public class adminServiceImpl implements AdminService {

    private final JdbcTemplate jdbcTemplate;
    private AdminRepository adminRepository ;

    private PrinterRepository printerRepository ;

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





}
