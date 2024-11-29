package com.example.SE_project.reposistory;

import com.example.SE_project.entity.Account;
import com.example.SE_project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student,String> {

    @Query(value = "CALL UpdateNbOfPageLeft(:std_id, :page_add)", nativeQuery = true)
    List<Object[]> update_nb_of_page(@Param("std_id") String std_id,
                                    @Param("page_add") int page_add);
    @Query(value = "CALL countstudent()", nativeQuery = true)
    List<Object[]> countstudent();

    Student findByAccount(Account account);

}