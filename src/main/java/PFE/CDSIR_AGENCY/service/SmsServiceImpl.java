//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.service.notification.SmsService;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
	private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	private final String accountSid;
	private final String authToken;
	private final String fromPhoneNumber;

	public SmsServiceImpl(@Value("${app.sms.twilio.account-sid}") String accountSid, @Value("${app.sms.twilio.auth-token}") String authToken, @Value("${app.sms.twilio.from-number}") String fromPhoneNumber) {
		this.accountSid = accountSid;
		this.authToken = authToken;
		this.fromPhoneNumber = fromPhoneNumber;
		if (accountSid != null && authToken != null && !accountSid.isEmpty() && !authToken.isEmpty()) {
			try {
				Twilio.init(accountSid, authToken);
				logger.info("Twilio client initialized successfully.");
			} catch (Exception e) {
				logger.error("Failed to initialize Twilio client: {}", e.getMessage(), e);
			}
		} else {
			logger.warn("Twilio credentials are not fully configured. SMS service may not function.");
		}

	}

	public void sendSms(String toPhoneNumber, String messageBody) {
		if (this.accountSid != null && !this.accountSid.isEmpty() && this.authToken != null && !this.authToken.isEmpty() && this.fromPhoneNumber != null && !this.fromPhoneNumber.isEmpty()) {
			try {
				Message message = (Message)Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(this.fromPhoneNumber), messageBody).create();
				logger.info("SMS sent to '{}'. SID: {}", toPhoneNumber, message.getSid());
			} catch (ApiException e) {
				logger.error("Twilio API error sending SMS to '{}': {}", toPhoneNumber, e.getMessage());
			} catch (Exception e) {
				logger.error("Unexpected error sending SMS to '{}': {}", new Object[]{toPhoneNumber, e.getMessage(), e});
			}

		} else {
			logger.error("SMS service not fully configured. Please check 'app.sms.twilio' properties in application.properties.");
		}
	}
}
