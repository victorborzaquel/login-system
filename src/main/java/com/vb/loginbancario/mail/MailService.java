package com.vb.loginbancario.mail;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailRepository repositirory;

    private final JavaMailSender emailSender;

    @Transactional
    public void sendEmail(Mail mail) {
        mail.setSendAt(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(mail.getEmailFrom());
            message.setTo(mail.getEmailTo());
            message.setSubject(mail.getSubject());
            message.setText(mail.getText());

            emailSender.send(message);

            mail.setEmailStatus(EmailStatus.SENT);
        } catch (MailException e) {
            mail.setEmailStatus(EmailStatus.ERROR);
        } finally {
            repositirory.save(mail);
        }
    }
}
