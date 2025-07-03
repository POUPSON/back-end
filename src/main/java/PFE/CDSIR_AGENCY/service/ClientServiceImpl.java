package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.config.JwtUtils;
import PFE.CDSIR_AGENCY.dto.LoginResponse;
import PFE.CDSIR_AGENCY.dto.PasswordResetRequest;
import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.entity.Client.Role;
import PFE.CDSIR_AGENCY.exception.AccountNotEnabledException;
import PFE.CDSIR_AGENCY.exception.DuplicateCniException;
import PFE.CDSIR_AGENCY.exception.DuplicateEmailException;
import PFE.CDSIR_AGENCY.exception.DuplicatePhoneException;
import PFE.CDSIR_AGENCY.exception.InvalidCredentialsException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import PFE.CDSIR_AGENCY.service.ClientService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
	private final ClientRepository clientRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final JavaMailSender mailSender;
	@Value("${app.password.reset.url}")
	private String passwordResetUrl;
	@Value("${spring.mail.username}")
	private String emailFrom;

	// Votre constructeur existant
	public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, JavaMailSender mailSender, @Value("${app.password.reset.url}") String passwordResetUrl, @Value("${spring.mail.username}") String emailFrom) {
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
		this.mailSender = mailSender;
		this.passwordResetUrl = passwordResetUrl;
		this.emailFrom = emailFrom;
	}

	public Client enregistrerClient(Client client) {
		if (this.clientRepository.findByEmail(client.getEmail()).isPresent()) {
			throw new DuplicateEmailException("L'email '" + client.getEmail() + "' est déjà utilisé.");
		} else if (this.clientRepository.findByTelephone(client.getTelephone()).isPresent()) {
			throw new DuplicatePhoneException("Le numéro de téléphone '" + client.getTelephone() + "' est déjà utilisé.");
		} else if (client.getNumeroCni() != null && this.clientRepository.findByNumeroCni(client.getNumeroCni()).isPresent()) {
			throw new DuplicateCniException("Le numéro CNI '" + client.getNumeroCni() + "' est déjà utilisé.");
		} else {
			client.setMotPasse(this.passwordEncoder.encode(client.getMotPasse()));
			if (client.getRole() == null) {
				client.setRole(Role.CLIENT);
			}

			return (Client)this.clientRepository.save(client);
		}
	}

	public LoginResponse loginClient(String email, String motPasse) {
		Client client = this.clientRepository.findByEmail(email).orElseThrow(() -> {
			log.warn("Tentative de connexion échouée pour l'email : {}", email);
			return new InvalidCredentialsException("Email ou mot de passe incorrect.");
		});

		// >>>>>>>>>>>>> LIGNES DE DÉBOGAGE À AJOUTER/VÉRIFIER <<<<<<<<<<<<<
		log.info("DEBUG Login: Email reçu: {}", email);
		// ATTENTION: Ne logguez JAMAIS le mot de passe en clair en production.
		// Ici, nous logguons seulement une petite partie pour le débogage.
		log.info("DEBUG Login: Mot de passe en clair reçu (partiel pour sécurité): {}...", motPasse.substring(0, Math.min(motPasse.length(), 3))); 
		log.info("DEBUG Login: Mot de passe haché de la BD: {}", client.getMotPasse());
		log.info("DEBUG Login: passwordEncoder.matches() résultat: {}", this.passwordEncoder.matches(motPasse, client.getMotPasse()));
		// >>>>>>>>>>>>> FIN DES LIGNES DE DÉBOGAGE <<<<<<<<<<<<<

		if (!this.passwordEncoder.matches(motPasse, client.getMotPasse())) {
			this.incrementFailedLoginAttempts(client);
			throw new InvalidCredentialsException("Email ou mot de passe incorrect.");
		}

		if (this.isAccountLocked(client.getId())) {
			log.warn("Compte client avec ID {} est verrouillé.", client.getId());
			throw new AccountNotEnabledException("Votre compte est verrouillé.");
		}

		if (!client.isEnabled()) {
			log.warn("Compte client avec ID {} n'est pas activé.", client.getId());
			throw new AccountNotEnabledException("Votre compte n'est pas activé.");
		}

		this.resetFailedLoginAttempts(client.getId());
		String token = this.jwtUtils.generateToken(client);
		this.updateLastLogin(client.getId());
		this.resetFailedLoginAttempts(client.getId()); // Double appel ?
		this.updateLastLogin(client.getId()); // Double appel ?

		LoginResponse response = new LoginResponse();
		response.setSuccess(true);
		response.setToken(token);
		response.setMessage("Connexion réussie pour le client " + client.getEmail());
		response.setUserId(client.getId());
		response.setNom(client.getNom());
		response.setPrenom(client.getPrenom());
		response.setEmail(client.getEmail());
		response.setRole(client.getRole().name());

		return response;
	}

	public void initierReinitialisationMotDePasse(String email) {
		Client client = (Client)this.clientRepository.findByEmail(email).orElseThrow(() -> {
			return new NotFoundException("Aucun client trouvé avec cet email.");
		});
		String token = UUID.randomUUID().toString();
		client.setResetToken(token);
		client.setTokenExpiration(LocalDateTime.now());
		this.clientRepository.save(client);
		this.envoyerEmailReinitialisationMotDePasse(email, token);
		log.info("Lien de réinitialisation de mot de passe généré et envoyé à {}", email);
	}

	public void completerReinitialisationMotDePasse(PasswordResetRequest request) {
		Client client = this.clientRepository.findByResetToken(request.getToken()).orElseThrow(() -> {
			return new NotFoundException("Token de réinitialisation invalide ou expiré.");
		});
		if (client.getTokenExpiration() == null || client.getTokenExpiration().isBefore(LocalDateTime.now())) {
			throw new NotFoundException("Token de réinitialisation invalide ou expiré.");
		} else {
			client.setMotPasse(this.passwordEncoder.encode(request.getNewPassword()));
			client.setResetToken((String)null);
			client.setTokenExpiration((LocalDateTime)null);
			this.clientRepository.save(client);
			log.info("Mot de passe réinitialisé pour le client avec l'email {}", client.getEmail());
		}
	}

	public Long getCurrentAuthenticatedClientId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			String username = ((UserDetails)authentication.getPrincipal()).getUsername();
			Client client = (Client)this.clientRepository.findByEmail(username).orElseThrow(() -> {
				return new NotFoundException("Client non trouvé avec l'email d'authentification : " + username);
			});
			return client.getId();
		} else {
			throw new IllegalStateException("Aucun client authentifié trouvé.");
		}
	}

	public Client getClientById(Long id) {
		return (Client)this.clientRepository.findById(id).orElseThrow(() -> {
			return new NotFoundException("Client non trouvé avec l'ID : " + id);
		});
	}

	public Client updateClient(Long id, Client updatedClient) {
		Client existingClient = (Client)this.clientRepository.findById(id).orElseThrow(() -> {
			return new NotFoundException("Client non trouvé avec l'ID : " + id);
		});
		Optional<Client> clientWithSameEmail = this.clientRepository.findByEmail(updatedClient.getEmail());
		if (clientWithSameEmail.isPresent() && !clientWithSameEmail.get().getId().equals(id)) {
			throw new DuplicateEmailException("L'email '" + updatedClient.getEmail() + "' est déjà utilisé par un autre client.");
		} else {
			Optional<Client> clientWithSamePhone = this.clientRepository.findByTelephone(updatedClient.getTelephone());
			if (clientWithSamePhone.isPresent() && !clientWithSamePhone.get().getId().equals(id)) {
				throw new DuplicatePhoneException("Le numéro de téléphone '" + updatedClient.getTelephone() + "' est déjà utilisé par un autre client.");
			} else {
				if (updatedClient.getNumeroCni() != null) {
					Optional<Client> clientWithSameCni = this.clientRepository.findByNumeroCni(updatedClient.getNumeroCni());
					if (clientWithSameCni.isPresent() && !clientWithSameCni.get().getId().equals(id)) {
						throw new DuplicateCniException("Le numéro CNI '" + updatedClient.getNumeroCni() + "' est déjà utilisé par un autre client.");
					}
				}

				existingClient.setNom(updatedClient.getNom());
				existingClient.setPrenom(updatedClient.getPrenom());
				existingClient.setEmail(updatedClient.getEmail());
				existingClient.setTelephone(updatedClient.getTelephone());
				existingClient.setAdresse(updatedClient.getAdresse());
				existingClient.setNumeroCni(updatedClient.getNumeroCni());
				existingClient.setDateNaissance(updatedClient.getDateNaissance());
				existingClient.setLieuResidence(updatedClient.getLieuResidence());
				if (updatedClient.getMotPasse() != null && !updatedClient.getMotPasse().isEmpty()) {
					existingClient.setMotPasse(this.passwordEncoder.encode(updatedClient.getMotPasse()));
				}

				return (Client)this.clientRepository.save(existingClient);
			}
		}
	}

	public List<Client> getAllClients() {
		return this.clientRepository.findAll();
	}

	public Client createClient(Client client) {
		if (this.clientRepository.findByEmail(client.getEmail()).isPresent()) {
			throw new DuplicateEmailException("L'email '" + client.getEmail() + "' est déjà utilisé.");
		} else if (this.clientRepository.findByTelephone(client.getTelephone()).isPresent()) {
			throw new DuplicatePhoneException("Le numéro de téléphone '" + client.getTelephone() + "' est déjà utilisé.");
		} else if (client.getNumeroCni() != null && this.clientRepository.findByNumeroCni(client.getNumeroCni()).isPresent()) {
			throw new DuplicateCniException("Le numéro CNI '" + client.getNumeroCni() + "' est déjà utilisé.");
		} else {
			client.setMotPasse(this.passwordEncoder.encode(client.getMotPasse()));
			if (client.getRole() == null) {
				client.setRole(Role.CLIENT);
			}

			return (Client)this.clientRepository.save(client);
		}
	}

	public void deleteClient(Long id) {
		if (!this.clientRepository.existsById(id)) {
			throw new NotFoundException("Client non trouvé avec l'ID : " + id);
		} else {
			this.clientRepository.deleteById(id);
			log.info("Client avec ID {} supprimé avec succès", id);
		}
	}

	private void envoyerEmailReinitialisationMotDePasse(String email, String token) {
		try {
			String resetLink = this.passwordResetUrl + "?token=" + token;
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(this.emailFrom);
			message.setTo(email);
			message.setSubject("Réinitialisation de mot de passe - CDSIR Agency");
			message.setText("Cliquez sur ce lien pour réinitialiser votre mot de passe :\\n" + resetLink + "\\n\\nCe lien expirera dans 2 heures.");
			this.mailSender.send(message);
		} catch (Exception e) {
			log.error("Échec de l'envoi de l'e-mail de réinitialisation du mot de passe à {}: {}", new Object[]{email, e.getMessage(), e});
		}

	}


	@Override
	public void lockClientAccount(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		client.setAccountLocked(true);
		clientRepository.save(client);
		log.warn("Compte client avec ID {} a été verrouillé.", clientId);
	}

	@Override
	public void unlockClientAccount(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		client.setAccountLocked(false);
		client.setFailedLoginAttempts(0); // Réinitialiser les tentatives de connexion échouées lors du déverrouillage
		clientRepository.save(client);
		log.info("Compte client avec ID {} a été déverrouillé et les tentatives de connexion réinitialisées.", clientId);
	}

	@Override
	public void enableTwoFactorAuthentication(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		client.setTwoFactorEnabled(true);
		clientRepository.save(client);
		log.info("Authentification à deux facteurs activée pour le client avec ID : {}", clientId);
	}

	@Override
	public void disableTwoFactorAuthentication(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		client.setTwoFactorEnabled(true);
		clientRepository.save(client);
		log.info("Authentification à deux facteurs désactivée pour le client avec ID : {}", clientId);
	}

	@Override
	public int getFailedLoginAttempts(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		return client.getFailedLoginAttempts();
	}

	// Méthode utilitaire pour incrémenter les tentatives de connexion échouées (peut être appelée par loginClient)
	private void incrementFailedLoginAttempts(Client client) {
		client.setFailedLoginAttempts(client.getFailedLoginAttempts() + 1);
		// Vous pouvez ajouter une logique ici pour verrouiller le compte après X tentatives
		if (client.getFailedLoginAttempts() >= 5) { // Exemple: verrouiller après 5 tentatives
			client.setAccountLocked(true);
			log.warn("Compte client avec ID {} verrouillé après 5 tentatives de connexion échouées.", client.getId());
		}
		clientRepository.save(client);
	}

	@Override
	public void resetFailedLoginAttempts(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		client.setFailedLoginAttempts(0);
		clientRepository.save(client);
		log.info("Tentatives de connexion échouées réinitialisées pour le client avec ID : {}", clientId);
	}

	@Override
	public boolean isAccountLocked(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		return client.isAccountLocked();
	}

	@Override
	public void updateLastLogin(Long clientId) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID : " + clientId));
		client.setLastLogin(LocalDateTime.now());
		clientRepository.save(client);
		log.info("Dernière connexion mise à jour pour le client avec ID : {}", clientId);
	}

	@Generated
	public ClientServiceImpl(final ClientRepository clientRepository, final PasswordEncoder passwordEncoder, final JwtUtils jwtUtils, final JavaMailSender mailSender, @Value("${app.password.reset.url}") final String passwordResetUrl, @Value("${spring.mail.username}") final String emailFrom) {
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
		this.mailSender = mailSender;
		this.passwordResetUrl = passwordResetUrl;
		this.emailFrom = emailFrom;
	}
}
ok je lai 
