package com.example.SE_project.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PrintKey implements Serializable {
//    @Column(name = "printer_id")
//    private String printerId;
    @Column(name = "order_num")
    private Integer orderNum;
    @Column(name = "file_id")
    private String fileId;
}
