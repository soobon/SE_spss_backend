package com.example.SE_project.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewFileDTO {
    private String file_name;
    private Integer num_pages;
}
