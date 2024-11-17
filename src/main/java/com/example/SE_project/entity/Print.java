package com.example.SE_project.entity;

import com.example.SE_project.entity.key.PrintKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "print")
public class Print {

    @EmbeddedId
    private PrintKey printKey;

    @Column(name = "nb_of_page_used", nullable = false)
    private Integer nb_of_page_used;
    @Column(name = "nb_of_copy", nullable = false)
    private Integer nb_of_copy;
    @Column(name = "paper_size",
            columnDefinition = "varchar(5) check (paper_size = 'A3'  or paper_size = 'A4')",
            nullable = false
    )
    private String paper_size;
    @Column(name = "print_date", nullable = false)
    private Date print_date;

    @ManyToOne
    @MapsId("printerId")
    @JoinColumn(name = "printer_id", columnDefinition = "char(9) check (printer_id like '_________')")
    @JsonIgnore
    private Printer printer;

    @ManyToOne
    @MapsId("fileId")
    @JoinColumn(name = "file_id", columnDefinition = "varchar(100)")
    private File file;
}
