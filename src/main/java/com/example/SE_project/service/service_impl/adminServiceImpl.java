package com.example.SE_project.service.service_impl;

import com.example.SE_project.dto.adminDTO;
import com.example.SE_project.dto.printerDTO;
import com.example.SE_project.entity.Admin;
import com.example.SE_project.entity.Printer;
import com.example.SE_project.exception.UserNotFound;
import com.example.SE_project.reposistory.AdminRepository;
import com.example.SE_project.reposistory.PrinterRepository;
import com.example.SE_project.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@AllArgsConstructor
public class adminServiceImpl implements AdminService {

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




}
