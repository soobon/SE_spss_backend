package com.example.SE_project.service;

import com.example.SE_project.dto.adminDTO;
import com.example.SE_project.dto.overallDTO;
import com.example.SE_project.dto.printerDTO;
import com.example.SE_project.dto.requestDTO;
import com.example.SE_project.entity.*;
import com.example.SE_project.entity.Printer;
import com.example.SE_project.entity.SPSS;


import java.sql.Date;
import java.util.List;

public interface AdminService {
    adminDTO find_admin_information (String id);

    List<printerDTO> get_all_printer ();

    printerDTO get_by_id(String id);

    List<requestDTO> get_all_print_request();

    public List<requestDTO> get_all_print_request_by_printer_id(String id);

    public List<requestDTO> get_all_print_request_by_student_id(String id);

    public Printer insertNewPrinter(String building, String model, Date date);

    public SPSS reset(String semester,Integer numOfPaperDefault);

    public List<Integer> generateSemesterCodeBaseOnRealTime();

    public printerDTO updateStatePrinter (String printer_id);

    public Print acceptPrint(/*String printer_id, */ String file_id , Integer orderNum);
    public Print refusePrint(Integer order_num , String file_id , Integer status);

    public overallDTO getOverall();
}
