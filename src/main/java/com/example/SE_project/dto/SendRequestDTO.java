package com.example.SE_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendRequestDTO {
    private String printer_id;
    private String file_id;
    private String paper_size;
    private Integer nb_of_copy;
    private Integer one_or_two_side;
}
