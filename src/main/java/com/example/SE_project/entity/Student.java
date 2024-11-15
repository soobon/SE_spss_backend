package com.example.SE_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "id",
            columnDefinition = "char(9) check (id like '_________')"
    )
    private String id;

    @Column(name = "namee", length = 20)
    private String namee;

    @Column(name = "email",
            columnDefinition = "varchar(40) check (email like '%_@_%')",
            nullable = false
    )
    private String email;

    @Column(name = "faculty", length = 20)
    private String faculty;

    @Column(name = "nb_of_page_left")
    private Integer nb_of_page_left;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;
}
