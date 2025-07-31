package com.shobhit.mailSender.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HrContact {
    private String company;
    private String email;

    public HrContact(String company, String email) {
        this.company = company;
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
