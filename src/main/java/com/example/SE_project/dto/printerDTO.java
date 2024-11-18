package com.example.SE_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class printerDTO {
    private String printer_id;
    private String building ;
    private Integer state ;
    private String model;
    private Date import_date;
}
