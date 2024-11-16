package com.example.SE_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "printer")
public class Printer {
    @Id
    @Column(name = "printer_id",
            columnDefinition = "char(9) check (printer_id like '_________')"
    )
    private String printer_id;
    @Column(name = "building", length = 10, nullable = false)
    private String building;
    @Column(name = "state",
            columnDefinition = "int check (state  = 0 or state = 1)"
    )
    private Integer state;
    @Column(name = "model", length = 20, nullable = false)
    private String model;
    @Column(name = "import_date", nullable = false)
    private Date import_date;
}
