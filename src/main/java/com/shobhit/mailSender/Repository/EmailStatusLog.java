package com.shobhit.mailSender.Repository;

import com.shobhit.mailSender.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailStatusLog extends JpaRepository<Recipient, Long> {
}
