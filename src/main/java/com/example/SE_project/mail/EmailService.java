package com.example.SE_project.mail;

public interface EmailService {
    String sendSimpleEmail(EmailDetails details);

    String sendEmailWithAttachment(EmailDetails details);
}
