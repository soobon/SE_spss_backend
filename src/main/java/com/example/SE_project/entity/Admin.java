package com.example.SE_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "adminn"
)
public class Admin {
    @Id
    @Column(
            name = "id",
            length = 9,
            columnDefinition = "char(9) check (id like '_________')"
    )
    private String id;
    @Column(name = "namee", length = 20)
    private String namee;
    @Column(name = "email",
            nullable = false,
            columnDefinition = "varchar(40) check (email like '%_@_%')"
    )
    private String email;

    @OneToOne
    @JoinColumn(name = "username",
                referencedColumnName = "username"
    )
    private Account account;
}
