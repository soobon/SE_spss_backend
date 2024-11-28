package com.example.SE_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    private Integer nb_of_page_left;
    private Integer total_page_used;
    private Integer printing_count;
//    private Integer printing_count_for_specific_month;
    private List<MonthDTO> monthDTOS;
}
