package com.shobhit.mailSender.Service;

import com.shobhit.mailSender.DTO.HrContact;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HrContactReader {
    public List<HrContact> readContactsFromFile(String filePath) throws IOException {
        List<HrContact> contacts = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                contacts.add(new HrContact(parts[0].trim(), parts[1].trim()));
            }
        }
        return contacts;
    }
}
