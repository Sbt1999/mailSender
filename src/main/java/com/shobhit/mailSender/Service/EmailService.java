package com.shobhit.mailSender.Service;

import com.shobhit.mailSender.DTO.HrContact;
import com.shobhit.mailSender.Repository.EmailStatusLog;
import com.shobhit.mailSender.model.Recipient;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;

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

            String htmlContent = "<p>Dear HR at <b>" + hr.getCompany() + "</b>,</p><p>I am writing to apply...</p>";
            helper.setText(htmlContent, true);

            File resumeFile = new File("src/main/resources/CV_Shobhit_Katiyar.pdf");
            helper.addAttachment(resumeFile.getName(), resumeFile);

            mailSender.send(message);

            log.setStatus("SUCCESS");
            log.setErrorMessage("");
        } catch (Exception e) {
            log.setStatus("FAILURE");
            log.setErrorMessage(e.getMessage());
        }

        logRepository.save(log);
    }
}

