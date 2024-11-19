package com.example.SE_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "spss")
public class SPSS {
    @Id
    @Column(name = "semester", length = 10)
    private String semester;
    @Column(name = "nb_of_page_default")
    private Integer nb_of_page_default;

    @Column(name = "nb_of_page_used")
    private Integer nb_of_page_used ;

    @Column(name = "reset_date")
    private Date reset_date;
}
