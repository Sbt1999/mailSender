package com.shobhit.mailSender.Service;

import com.shobhit.mailSender.DTO.HrContact;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service // Added missing annotation
public class HrContactReader {

    public List<HrContact> readContactsFromFile(String fileName) throws IOException {
        List<HrContact> contacts = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource(fileName);

            if (!resource.exists()) {
                throw new IOException("File not found: " + fileName + " in resources folder");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    line = line.trim();

                    if (line.isEmpty() || line.startsWith("#")) {
                        continue; // Skip empty lines and comments
                    }

                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String company = parts[0].trim();
                        String email = parts[1].trim();

                        if (!company.isEmpty() && !email.isEmpty() && isValidEmail(email)) {
                            contacts.add(new HrContact(company, email));
                        } else {
                            System.err.println("Invalid data at line " + lineNumber + ": " + line);
                        }
                    } else {
                        System.err.println("Invalid format at line " + lineNumber + ": " + line);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading contacts file: " + e.getMessage());
            throw e;
        }

        return contacts;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}