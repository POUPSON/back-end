//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.service.notification.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	private final JavaMailSender mailSender;
	@Value("${app.email.from}")
	private String fromEmail;

	public void sendSimpleEmail(String to, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(this.fromEmail);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			this.mailSender.send(message);
			logger.info("Email simple envoyé à '{}' avec le sujet : '{}'", to, subject);
		} catch (MailException e) {
			logger.error("Erreur lors de l'envoi de l'email simple à '{}' : {}", new Object[]{to, e.getMessage(), e});
		} catch (Exception e) {
			logger.error("Erreur inattendue lors de l'envoi de l'email simple à '{}' : {}", new Object[]{to, e.getMessage(), e});
		}

	}

	public void sendHtmlEmail(String to, String subject, String htmlBody) {
		MimeMessage message = this.mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(this.fromEmail);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlBody, true);
			this.mailSender.send(message);
			logger.info("Email HTML envoyé à '{}' avec le sujet : '{}'", to, subject);
		} catch (MessagingException e) {
			logger.error("Erreur lors de l'envoi de l'email HTML à '{}' : {}", new Object[]{to, e.getMessage(), e});
		} catch (Exception e) {
			logger.error("Erreur inattendue lors de l'envoi de l'email HTML à '{}' : {}", new Object[]{to, e.getMessage(), e});
		}

	}

	@Generated
	public EmailServiceImpl(final JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
