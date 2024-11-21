package com.example.SE_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class requestDTO {
    private String id ;
    private String file_name;
    private Integer nb_of_page_used;
    private Integer statuss ;
    private String print_date;
    private String building;
    private String print_id ;
    private String file_id;
}
