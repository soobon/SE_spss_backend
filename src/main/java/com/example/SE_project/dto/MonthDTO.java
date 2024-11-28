package com.example.SE_project.dto;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthDTO {
    private String month;
    private Integer printing_count_for_this_month;
}