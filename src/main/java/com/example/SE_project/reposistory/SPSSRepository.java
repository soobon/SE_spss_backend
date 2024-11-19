package com.example.SE_project.reposistory;

import com.example.SE_project.entity.SPSS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SPSSRepository extends JpaRepository<SPSS, String> {
    public SPSS findBySemester(String semester);
}
