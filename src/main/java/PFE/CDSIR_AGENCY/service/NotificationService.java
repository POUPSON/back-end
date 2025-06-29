//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            this.mailSender.send(message);
            log.info("Email sent successfully to {} with subject: {}", to, subject);
        } catch (MailException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail: " + e.getMessage());
        }
    }

    public void sendNotification(Long userId, String title, String message, String type) {
        log.info("Sending notification to user {}: Type={}, Title='{}', Message='{}'", new Object[]{userId, type, title, message});
    }

    public void sendSms(String toPhoneNumber, String message) {
        log.info("Attempting to send SMS to {}: {}", toPhoneNumber, message);
        log.warn("L'envoi de SMS n'est pas encore implémenté. Veuillez intégrer une API SMS (ex: Twilio) pour cette fonctionnalité.");
    }

    @Generated
    public NotificationService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
