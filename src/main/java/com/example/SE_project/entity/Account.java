package com.example.SE_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "accounts"

)
public class Account {
    @Id
    @Column(name = "username", length = 20)
    private String username;
    @Column(name = "passwords", length = 20)
    private String password;
    @Column(name = "rolee",
            columnDefinition = "int check (rolee = 1 OR rolee = 2)"
    )
    private Integer role;
}
