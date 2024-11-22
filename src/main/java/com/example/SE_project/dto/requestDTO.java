package com.example.SE_project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class requestDTO {
    private String id ;
    private String file_name;
    private Integer nb_of_page_used;
    private Integer statuss ;
    private String print_date;
    private String building;
    private String print_id ;
    private String file_id;
    private Integer order_num;
}
