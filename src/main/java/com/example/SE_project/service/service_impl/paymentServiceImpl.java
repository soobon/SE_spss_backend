package com.example.SE_project.service.service_impl;

import com.example.SE_project.entity.Printer;
import com.example.SE_project.entity.Student;
import com.example.SE_project.reposistory.StudentRepository;
import com.example.SE_project.service.paymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class paymentServiceImpl implements paymentService {

    private StudentRepository studentRepository ;

    public Student buy_more_page(Integer nb_page , String studentId) {
        List<Object[]> student  = studentRepository.update_nb_of_page(studentId , nb_page);
        Object[] row = student.get(0);
        Student std  = new Student();
        std.setId((String) row[0] );
        std.setName((String) row[1]);
        std.setEmail((String) row[2]);
        std.setFaculty((String) row[3]);
        std.setNb_of_page_left((Integer) row[4]);
        return std;
    }

}
