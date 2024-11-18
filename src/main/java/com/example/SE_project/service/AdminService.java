package com.example.SE_project.service;

import com.example.SE_project.dto.adminDTO;
import com.example.SE_project.dto.printerDTO;
import com.example.SE_project.dto.requestDTO;
import com.example.SE_project.entity.Printer;

import java.sql.Date;
import java.util.List;

public interface AdminService {
    adminDTO find_admin_information (String id);

    List<printerDTO> get_all_printer ();

    List<requestDTO> get_all_print_request();

    public List<requestDTO> get_all_print_request_by_printer_id(String id);

    public List<requestDTO> get_all_print_request_by_student_id(String id);

    public Printer insertNewPrinter(String building, String model, Date date);

}
