package com.example.SE_project.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class printKey implements Serializable {
    private String printer_id;
    private String file_id;
}
