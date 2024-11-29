package com.example.SE_project.reposistory;

import com.example.SE_project.dto.requestDTO;
import com.example.SE_project.entity.Account;
import com.example.SE_project.entity.Admin;
import com.example.SE_project.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findAdminById(String id);

    Admin findByAccount(Account account);

    @Query(value = "CALL GetStudentPrintDetails()", nativeQuery = true)
    List<Object[]> getStudentPrintDetails();
    @Query(value = "CALL GetStudentPrintDetailsByPrinterId(:printer_id)", nativeQuery = true)
    List<Object[]> GetStudentPrintDetailsByPrinterId (@Param("printer_id") String printer_id);

    @Query(value = "CALL  GetStudentPrintDetailsByStudentId (:student_id)", nativeQuery = true)
    List<Object[]> GetStudentPrintDetailsByStudentId (@Param("student_id") String student_id);

    @Query(value = "CALL InsertPrintertet(:building, :model, :imp_date)", nativeQuery = true)
    List<Object[]> insertNewPrinter(@Param("building") String building,
                                    @Param("model") String model,
                                    @Param("imp_date") java.sql.Date impDate);



}
