package com.example.SE_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileHistoryDTO {
    private String file_name;
    private Integer num_pages;
    private Date upload_date;
    private List<PrintDTO> printDTOList;
}
