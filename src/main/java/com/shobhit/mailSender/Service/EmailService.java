package com.shobhit.mailSender.Service;

import com.shobhit.mailSender.DTO.HrContact;
import com.shobhit.mailSender.Repository.EmailStatusLog;
import com.shobhit.mailSender.model.Recipient;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service // Added missing annotation
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailStatusLog logRepository;

    public void sendEmailToHr(HrContact hr) {
        Recipient log = new Recipient();
        log.setCompanyName(hr.getCompany());
        log.setHrEmail(hr.getEmail());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(hr.getEmail());
            helper.setSubject("Application for Java Developer Role at " + hr.getCompany());
            helper.setFrom("your-email@gmail.com", "Shobhit"); // Add from email

            String htmlContent = buildEmailContent(hr.getCompany());
            helper.setText(htmlContent, true);

            // Fix file path - use ClassPathResource for files in resources folder
            try {
                ClassPathResource resumeFile = new ClassPathResource("CV_Shobhit_Katiyar.pdf");
                if (resumeFile.exists()) {
                    helper.addAttachment("CV_Shobhit_Katiyar.pdf", resumeFile);
                } else {
                    System.err.println("Resume file not found in resources folder");
                }
            } catch (Exception e) {
                System.err.println("Error attaching resume: " + e.getMessage());
            }

            mailSender.send(message);

            log.setStatus("SUCCESS");
            log.setErrorMessage("");
            System.out.println("Email sent successfully to: " + hr.getEmail());

        } catch (Exception e) {
            log.setStatus("FAILURE");
            log.setErrorMessage(e.getMessage());
            System.err.println("Failed to send email to " + hr.getEmail() + ": " + e.getMessage());
        }

        logRepository.save(log);
    }

    private String buildEmailContent(String companyName) {
        return "<html><body>" +
                "<p>Dear HR Team at <b>" + companyName + "</b>,</p>" +
                "<p>I hope this email finds you well. I am writing to express my interest in the Java Developer position at " + companyName + ".</p>" +
                "<p>I am a passionate Java developer with experience in:</p>" +
                "<ul>" +
                "<li>Spring Boot & Spring Framework</li>" +
                "<li>RESTful API development</li>" +
                "<li>Database management (MySQL, PostgreSQL)</li>" +
                "<li>Microservices architecture</li>" +
                "</ul>" +
                "<p>Please find my resume attached for your review. I would welcome the opportunity to discuss how my skills and enthusiasm can contribute to your team.</p>" +
                "<p>Thank you for your time and consideration.</p>" +
                "<p>Best regards,<br>Shobhit Katiyar</p>" +
                "</body></html>";
    }
}

