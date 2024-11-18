package com.example.SE_project.service;

import com.example.SE_project.dto.adminDTO;
import com.example.SE_project.dto.printerDTO;
import com.example.SE_project.entity.Printer;

import java.util.List;

public interface AdminService {
    adminDTO find_admin_information (String id);

    List<printerDTO> get_all_printer ();
}
