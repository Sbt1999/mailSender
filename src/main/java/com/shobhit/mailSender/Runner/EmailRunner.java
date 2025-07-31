package com.shobhit.mailSender.Runner;

import com.shobhit.mailSender.DTO.HrContact;
import com.shobhit.mailSender.Service.EmailService;
import com.shobhit.mailSender.Service.HrContactReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

public class EmailRunner implements CommandLineRunner {

    @Autowired
    private HrContactReader contactReader;

    @Autowired
    private EmailService emailService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("EmailRunner started...");
//        List<HrContact> contacts = contactReader.readContactsFromFile("src/main/resources/hr_contacts.txt");
        List<HrContact> contacts = contactReader.readContactsFromFile("hr_contacts.txt");
        for (HrContact contact : contacts) {
            emailService.sendEmailToHr(contact);
        }
    }
}
