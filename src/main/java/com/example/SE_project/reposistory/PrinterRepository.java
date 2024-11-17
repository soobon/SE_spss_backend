package com.example.SE_project.reposistory;

import com.example.SE_project.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrinterRepository extends JpaRepository<Printer,String> {
}
