package com.shobhit.mailSender.Runner;

import com.shobhit.mailSender.DTO.HrContact;
import com.shobhit.mailSender.Service.EmailService;
import com.shobhit.mailSender.Service.HrContactReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // Added missing annotation
public class EmailRunner implements CommandLineRunner {

    @Autowired
    private HrContactReader contactReader;

    @Autowired
    private EmailService emailService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("EmailRunner started...");
        try {
            List<HrContact> contacts = contactReader.readContactsFromFile("hr_contacts.txt");
            System.out.println("Found " + contacts.size() + " contacts");

            for (HrContact contact : contacts) {
                System.out.println("Sending email to: " + contact.getCompany() + " - " + contact.getEmail());
                emailService.sendEmailToHr(contact);
                Thread.sleep(2000); // Add delay between emails to avoid spam detection
            }
            System.out.println("Email sending completed!");
        } catch (Exception e) {
            System.err.println("Error in EmailRunner: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
