package com.example.SE_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files")
@Builder
public class File {
    @Id
    @Column(name = "file_id", length = 100)
    private String fileid;
    @Column(name = "upload_date", nullable = false)
    private Date upload_date;
    @Column(name = "file_name", length = 20, nullable = false)
    private String file_name;
    @Column(name = "num_pages",nullable = false)
    private Integer num_pages;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    @JsonBackReference
    private Student student;

    @OneToMany(mappedBy = "file")
    @JsonIgnore
    private List<Print> print_list;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL)
    @JsonIgnore
    private storage fileStorage;
}
