package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.dto.LoginResponse;
import PFE.CDSIR_AGENCY.dto.PasswordResetRequest;
import PFE.CDSIR_AGENCY.entity.Client;
import java.util.List;

public interface ClientService {
	Client enregistrerClient(Client client);

	LoginResponse loginClient(String email, String mot_passe);

	void initierReinitialisationMotDePasse(String email);

	void completerReinitialisationMotDePasse(PasswordResetRequest request);

	Long getCurrentAuthenticatedClientId();

	Client getClientById(Long id);

	Client updateClient(Long id, Client updatedClient);

	List<Client> getAllClients();

	Client createClient(Client client);

	void deleteClient(Long id);

	// Nouvelles méthodes ajoutées
	void lockClientAccount(Long clientId);
	void unlockClientAccount(Long clientId);
	void enableTwoFactorAuthentication(Long clientId);
	void disableTwoFactorAuthentication(Long clientId);
	int getFailedLoginAttempts(Long clientId);
	void resetFailedLoginAttempts(Long clientId);
	boolean isAccountLocked(Long clientId);
	void updateLastLogin(Long clientId);
}