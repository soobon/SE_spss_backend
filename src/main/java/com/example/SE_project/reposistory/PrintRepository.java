package com.example.SE_project.reposistory;

import com.example.SE_project.entity.Print;
import com.example.SE_project.entity.key.PrintKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


public interface PrintRepository extends JpaRepository<Print, PrintKey> {
    Print findByPrintKey_FileIdAndPrintKey_PrinterId(String fileId, String printerId);

    @Query(value = "SELECT SUM(nb_of_page_used) " +
            "FROM print P " +
            "JOIN (SELECT S.id, F.file_id " +
            "FROM student S " +
            "JOIN files F ON S.id = F.id " +
            "WHERE S.id = :studentId) T " +
            "ON P.file_id = T.file_id", nativeQuery = true)
    Integer findTotalPagesUsedByStudent(@Param("studentId") Integer studentId);

    @Query(value = """
        SELECT count(print_date)
        FROM print p
        JOIN (
            SELECT s.id, f.file_id
            FROM student s
            JOIN files f ON s.id = f.id
            WHERE s.id = :studentId
        ) t ON p.file_id = t.file_id
        WHERE MONTH(p.print_date) = :month
    """, nativeQuery = true)
    Integer findPrintCountForSpecificMonth(
            @Param("studentId") Integer studentId,
            @Param("month") Integer month
    );
}

