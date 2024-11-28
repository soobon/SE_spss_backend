package com.example.SE_project.reposistory;

import com.example.SE_project.entity.Print;
import com.example.SE_project.entity.key.PrintKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


public interface PrintRepository extends JpaRepository<Print, PrintKey> {
//    Print findByPrintKey_FileIdAndPrintKey_PrinterId(String fileId, String printerId);

    Print findByPrintKey_FileIdAndPrintKey_OrderNum(String fileId, Integer orderNum);

    @Query(value = "SELECT SUM(nb_of_page_used) " +
            "FROM print P " +
            "JOIN (SELECT S.id, F.file_id " +
            "FROM student S " +
            "JOIN files F ON S.id = F.id " +
            "WHERE S.id = :studentId) T " +
            "ON P.file_id = T.file_id WHERE P.statuss = 2", nativeQuery = true)
    Integer findTotalPagesUsedByStudent(@Param("studentId") String studentId);

    @Query(value = """
        SELECT count(print_date)
        FROM print p
        JOIN (
            SELECT s.id, f.file_id
            FROM student s
            JOIN files f ON s.id = f.id
            WHERE s.id = :studentId
        ) t ON p.file_id = t.file_id
        WHERE MONTH(p.print_date) = :month AND YEAR(p.print_date) =:year AND p.statuss = 2
    """, nativeQuery = true)
    Integer findPrintCountForSpecificMonth(
            @Param("studentId") String studentId,
            @Param("month") Integer month,
            @Param("year") Integer year
    );

    @Query(value = "CALL UpdatePrintStatus(:p_order_num ,:p_file_id , :p_status)", nativeQuery = true)
    List<Object[]> updatePrintStatus(@Param("p_order_num") Integer p_order_num,
                                     @Param("p_file_id") String p_file_id ,
                                     @Param ("p_status") Integer p_status);
    @Query(value = "CALL countrequest0()", nativeQuery = true)
    List<Object[]> countrequest0();

    List<Print> findAllByFile_Fileid(String file_id);

    @Query(value = "CALL UpdateOrderNum()", nativeQuery = true)
    List<Object[]> updateOrderNum();


}

