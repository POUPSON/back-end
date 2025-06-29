package PFE.CDSIR_AGENCY.service.notification;

// PAS BESOIN D'IMPORTS TWILIO, @Value, @Service, Logger DANS L'INTERFACE
// Ces éléments appartiennent à l'implémentation (SmsServiceImpl)
// car l'interface définit juste le contrat, pas les détails techniques.

public interface SmsService { // CHANGEMENT CRUCIAL: "class" devient "interface"

	void sendSms(String toPhoneNumber, String message);

	// Si vous aviez d'autres méthodes liées aux SMS, elles seraient déclarées ici
	// Exemple :
	// boolean checkSmsStatus(String messageSid);
}