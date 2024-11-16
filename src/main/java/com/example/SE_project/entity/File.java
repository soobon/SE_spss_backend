package com.example.SE_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @Column(name = "file_id", length = 100)
    private String file_id;
    @Column(name = "upload_date", nullable = false)
    private Date upload_date;
    @Column(name = "file_name", length = 20, nullable = false)
    private String file_name;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Student student;
}
