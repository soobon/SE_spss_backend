package com.example.SE_project.entity;

import com.example.SE_project.entity.key.printKey;
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
@IdClass(printKey.class)
public class Print {
    @Id
    @Column(name = "printer_id",
            columnDefinition = "char(9)",
            nullable = false
    )
    private String printer_id;
    @Id
    @Column(name = "file_id", length = 100, nullable = false)
    private String file_id;
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
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "printer_id", referencedColumnName = "printer_id")
    private Printer printer;
}
