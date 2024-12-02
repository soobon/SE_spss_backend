package com.example.SE_project.reposistory;

import com.example.SE_project.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrinterRepository extends JpaRepository<Printer,String> {

    @Query(value = "CALL UpdatePrinterState(:printer_id)", nativeQuery = true)
    List<Object[]> UpdatePrinterState(@Param("printer_id") String printer_id);
    @Query(value = "CALL GetPrinterById(:printer_id)", nativeQuery = true)
    List<Object[]> GetPrinterById(@Param("printer_id") String printer_id);
    @Query(value = "CALL countprinter()", nativeQuery = true)
    List<Object[]> countprinter();

    List<Printer> findAllByState(Integer state);
}
