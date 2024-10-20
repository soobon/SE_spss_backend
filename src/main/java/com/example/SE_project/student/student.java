package com.example.SE_project.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "student")
public class student {
    @Id
    private int student_id;
    @Column(name = "student_name")
    private String name ;
    @Column(name = "faculty")
    private String faculty;
    @Column(name = "nb_of_paper_left")
    private int nbPageLeft;
    @Column (name = "login_id")
    private int lgId;

}
