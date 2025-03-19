package com.example.SE_project.mail;

import com.example.SE_project.entity.Student;
import com.example.SE_project.reposistory.StudentRepository;
import com.example.SE_project.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@CrossOrigin("*")
@RequestMapping("/mail")
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final StudentRepository studentRepository;

    @PostMapping("/{id}")
    public ResponseEntity<?> sendEmail(
            @PathVariable String id
    ){
        Student student = studentRepository.findById(id).get();
        EmailDetails details = EmailDetails.builder()
                .subject("SPSS System")
                .recipient(student.getEmail())
                .attachment(null)
                .msgBody("Your request has been sent successfully!")
                .build();
        return new ResponseEntity<>(emailService.sendEmailWithAttachment(details), HttpStatus.OK);
    }

}
