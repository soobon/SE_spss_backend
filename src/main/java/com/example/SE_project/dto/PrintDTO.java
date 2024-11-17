package com.example.SE_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrintDTO {
    private Integer nb_of_page_used;
    private Integer nb_of_copy;
    private String paper_size;
    private Date print_date;
}
