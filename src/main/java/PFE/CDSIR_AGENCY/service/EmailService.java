//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.notification;

public interface EmailService {
	void sendSimpleEmail(String to, String subject, String body);

	void sendHtmlEmail(String to, String subject, String htmlBody);
}
