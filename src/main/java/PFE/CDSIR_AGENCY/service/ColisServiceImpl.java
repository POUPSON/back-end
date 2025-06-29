package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.dto.ColisRequestDto;
import PFE.CDSIR_AGENCY.dto.ColisResponseDto;
import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.entity.Colis;
import PFE.CDSIR_AGENCY.entity.Voyage;
import PFE.CDSIR_AGENCY.entity.Colis.ColisStatus;
import PFE.CDSIR_AGENCY.exception.BadRequestException;
import PFE.CDSIR_AGENCY.exception.ConflictException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.exception.OperationNotAllowedException;
import PFE.CDSIR_AGENCY.repository.AgenceRepository;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import PFE.CDSIR_AGENCY.repository.ColisRepository;
import PFE.CDSIR_AGENCY.repository.VoyageRepository;
import PFE.CDSIR_AGENCY.service.ColisService;
import PFE.CDSIR_AGENCY.service.NotificationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ColisServiceImpl implements ColisService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(ColisServiceImpl.class);

	private final ColisRepository colisRepository;
	private final AgenceRepository agenceRepository;
	private final ClientRepository clientRepository;
	private final VoyageRepository voyageRepository;
	private final NotificationService notificationService;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	// Configuration MTN Money
	@Value("${payment.mtn.api.url}")
	private String mtnMoneyApiUrl;
	@Value("${payment.mtn.api.key}")
	private String mtnApiKey;
	@Value("${payment.mtn.callback.url}")
	private String mtnCallbackUrl;

	// Configuration Orange Money
	@Value("${payment.orange.api.url}")
	private String orangeMoneyApiUrl;
	@Value("${payment.orange.merchant.id}")
	private String orangeMerchantId;
	@Value("${payment.orange.api.secret}")
	private String orangeApiSecret;
	@Value("${payment.orange.callback.url}")
	private String orangeCallbackUrl;

	public ColisServiceImpl(ColisRepository colisRepository,
							AgenceRepository agenceRepository,
							ClientRepository clientRepository,
							VoyageRepository voyageRepository,
							NotificationService notificationService,
							RestTemplate restTemplate,
							ObjectMapper objectMapper) {
		this.colisRepository = colisRepository;
		this.agenceRepository = agenceRepository;
		this.clientRepository = clientRepository;
		this.voyageRepository = voyageRepository;
		this.notificationService = notificationService;
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	public void verifyColisOwnership(Long colisId, Long clientId) throws OperationNotAllowedException {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		// Vérification si le client est l'expéditeur
		if (colis.getClientExpediteur() != null && colis.getClientExpediteur().getId().equals(clientId)) {
			return;
		}

		// Vérification si le client est le destinataire (par téléphone ou email)
		Optional<Client> clientOpt = clientRepository.findById(clientId);
		if (clientOpt.isPresent()) {
			Client client = clientOpt.get();
			if (client.getTelephone() != null && client.getTelephone().equals(colis.getNumeroDestinataire())) {
				return;
			}
			if (client.getEmail() != null && client.getEmail().equals(colis.getEmailDestinataire())) {
				return;
			}
		}

		throw new OperationNotAllowedException("Le client avec l'ID " + clientId + " n'est pas autorisé à accéder à ce colis");
	}

	@Override
	public ColisResponseDto createColis(ColisRequestDto colisRequestDto) {
		Agence agence = agenceRepository.findById(colisRequestDto.getAgenceId())
				.orElseThrow(() -> new NotFoundException("Agence non trouvée avec l'ID: " + colisRequestDto.getAgenceId()));

		if (colisRequestDto.getClientId() != null) {
			clientRepository.findById(colisRequestDto.getClientId())
					.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID: " + colisRequestDto.getClientId()));
		}

		Colis colis = mapToEntity(colisRequestDto);
		colis.setAgence(agence);
		colis.setTrackingNumber(generateUniqueTrackingNumber());
		colis.setDateEnregistrement(LocalDateTime.now());

		if (colisRequestDto.getClientId() != null) {
			Client client = clientRepository.findById(colisRequestDto.getClientId())
					.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID: " + colisRequestDto.getClientId()));
			colis.setClientExpediteur(client);
			colis.setNomExpediteur(null);
			colis.setTelephoneExpediteur(null);
			colis.setEmailExpediteur(null);
		} else {
			if (colisRequestDto.getSenderName() == null || colisRequestDto.getSenderName().isEmpty() ||
					colisRequestDto.getSenderPhone() == null || colisRequestDto.getSenderPhone().isEmpty() ||
					colisRequestDto.getSenderEmail() == null || colisRequestDto.getSenderEmail().isEmpty()) {
				throw new BadRequestException("Les informations de l'expéditeur (nom, téléphone, email) sont requises pour un colis sans client enregistré.");
			}

			colis.setNomExpediteur(colisRequestDto.getSenderName());
			colis.setTelephoneExpediteur(colisRequestDto.getSenderPhone());
			colis.setEmailExpediteur(colisRequestDto.getSenderEmail());
		}

		// Gestion du paiement
		String modePaiement = colisRequestDto.getModePaiement();
		if (modePaiement != null && !modePaiement.isEmpty()) {
			colis.setModePaiement(modePaiement);
			String paymentReference = UUID.randomUUID().toString();
			colis.setReferencePaiement(paymentReference);

			if ("MTN_MONEY".equalsIgnoreCase(modePaiement)) {
				boolean initiated = initiateMtnPayment(colisRequestDto.getSenderPhone(), colisRequestDto.getEstimatedCost(), paymentReference);
				if (!initiated) {
					throw new BadRequestException("Échec de l'initiation du paiement MTN Money. Veuillez réessayer.");
				}
				colis.setStatut(ColisStatus.EN_ATTENTE_PAIEMENT);
				log.info("Paiement MTN Money initié pour colis {} avec référence {}", colis.getTrackingNumber(), paymentReference);
			} else if ("ORANGE_MONEY".equalsIgnoreCase(modePaiement)) {
				boolean initiated = initiateOrangePayment(colisRequestDto.getSenderPhone(), colisRequestDto.getEstimatedCost(), paymentReference);
				if (!initiated) {
					throw new BadRequestException("Échec de l'initiation du paiement Orange Money. Veuillez réessayer.");
				}
				colis.setStatut(ColisStatus.EN_ATTENTE_PAIEMENT);
				log.info("Paiement Orange Money initié pour colis {} avec référence {}", colis.getTrackingNumber(), paymentReference);
			} else {
				log.warn("Mode de paiement non géré pour le colis: {}. Le colis reste en statut ENREGISTRE.", modePaiement);
				colis.setStatut(ColisStatus.ENREGISTRE);
			}
		} else {
			colis.setStatut(ColisStatus.ENREGISTRE);
		}

		Colis savedColis = colisRepository.save(colis);
		log.info("Colis créé avec succès: {}", savedColis.getTrackingNumber());
		return mapToDto(savedColis);
	}

	@Override
	public ColisResponseDto createColisForClient(Long clientId, ColisRequestDto colisRequestDto) {
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID: " + clientId));
		Agence agence = agenceRepository.findById(colisRequestDto.getAgenceId())
				.orElseThrow(() -> new NotFoundException("Agence non trouvée avec l'ID: " + colisRequestDto.getAgenceId()));

		Colis colis = mapToEntity(colisRequestDto);
		colis.setClientExpediteur(client);
		colis.setAgence(agence);
		colis.setTrackingNumber(generateUniqueTrackingNumber());
		colis.setDateEnregistrement(LocalDateTime.now());
		colis.setNomExpediteur(null);
		colis.setTelephoneExpediteur(null);
		colis.setEmailExpediteur(null);

		// Gestion du paiement
		String modePaiement = colisRequestDto.getModePaiement();
		if (modePaiement != null && !modePaiement.isEmpty()) {
			colis.setModePaiement(modePaiement);
			String paymentReference = UUID.randomUUID().toString();
			colis.setReferencePaiement(paymentReference);

			String senderPhoneNumber = client.getTelephone();

			if ("MTN_MONEY".equalsIgnoreCase(modePaiement)) {
				boolean initiated = initiateMtnPayment(senderPhoneNumber, colisRequestDto.getEstimatedCost(), paymentReference);
				if (!initiated) {
					throw new BadRequestException("Échec de l'initiation du paiement MTN Money. Veuillez réessayer.");
				}
				colis.setStatut(ColisStatus.EN_ATTENTE_PAIEMENT);
				log.info("Paiement MTN Money initié pour colis du client {} avec référence {}", colis.getTrackingNumber(), paymentReference);
			} else if ("ORANGE_MONEY".equalsIgnoreCase(modePaiement)) {
				boolean initiated = initiateOrangePayment(senderPhoneNumber, colisRequestDto.getEstimatedCost(), paymentReference);
				if (!initiated) {
					throw new BadRequestException("Échec de l'initiation du paiement Orange Money. Veuillez réessayer.");
				}
				colis.setStatut(ColisStatus.EN_ATTENTE_PAIEMENT);
				log.info("Paiement Orange Money initié pour colis du client {} avec référence {}", colis.getTrackingNumber(), paymentReference);
			} else {
				log.warn("Mode de paiement non géré pour le colis du client: {}. Le colis reste en statut ENREGISTRE.", modePaiement);
				colis.setStatut(ColisStatus.ENREGISTRE);
			}
		} else {
			colis.setStatut(ColisStatus.ENREGISTRE);
		}

		Colis savedColis = colisRepository.save(colis);
		log.info("Colis créé par le client {} avec succès: {}", clientId, savedColis.getTrackingNumber());
		return mapToDto(savedColis);
	}

	@Override
	public ColisResponseDto getColisById(Long id) {
		Colis colis = colisRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + id));
		return mapToDto(colis);
	}

	@Override
	public ColisResponseDto getColisByIdAndClientId(Long colisId, Long clientId) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		Optional<Client> clientOpt = clientRepository.findById(clientId);
		String clientPhone = clientOpt.map(Client::getTelephone).orElse(null);
		String clientEmail = clientOpt.map(Client::getEmail).orElse(null);

		boolean isSender = colis.getClientExpediteur() != null &&
				colis.getClientExpediteur().getId().equals(clientId);
		boolean isRecipient = colis.getNumeroDestinataire() != null &&
				colis.getNumeroDestinataire().equals(clientPhone);
		boolean isRecipientEmail = colis.getEmailDestinataire() != null &&
				colis.getEmailDestinataire().equals(clientEmail);

		if (!isSender && !isRecipient && !isRecipientEmail) {
			throw new OperationNotAllowedException("Accès non autorisé au colis pour le client avec l'ID: " + clientId);
		}
		return mapToDto(colis);
	}

	@Override
	public List<ColisResponseDto> getAllColis() {
		return colisRepository.findAll().stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ColisResponseDto> getColisBySenderId(Long senderId) {
		List<Colis> colisList = colisRepository.findByClientExpediteurId(senderId);
		if (colisList.isEmpty()) {
			Client senderClient = clientRepository.findById(senderId)
					.orElseThrow(() -> new NotFoundException("Client expéditeur non trouvé avec l'ID: " + senderId));

			if (senderClient.getEmail() != null) {
				colisList.addAll(colisRepository.findByEmailExpediteur(senderClient.getEmail()));
			}

			if (senderClient.getTelephone() != null) {
				colisList.addAll(colisRepository.findByTelephoneExpediteur(senderClient.getTelephone()));
			}
		}

		return colisList.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ColisResponseDto> getColisByRecipientId(Long recipientId) {
		Client recipientClient = clientRepository.findById(recipientId)
				.orElseThrow(() -> new NotFoundException("Client destinataire non trouvé avec l'ID: " + recipientId));

		List<Colis> colisList = colisRepository.findByNumeroDestinataireOrEmailDestinataire(
				recipientClient.getTelephone(), recipientClient.getEmail());

		return colisList.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public ColisResponseDto updateColis(Long id, ColisRequestDto colisRequestDto) {
		Colis existingColis = colisRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + id));

		existingColis.setDescription(colisRequestDto.getDescription());
		existingColis.setWeight(colisRequestDto.getWeight());
		existingColis.setDimensions(colisRequestDto.getDimensions());
		existingColis.setEstimatedCost(colisRequestDto.getEstimatedCost());
		existingColis.setNomExpediteur(colisRequestDto.getSenderName());
		existingColis.setTelephoneExpediteur(colisRequestDto.getSenderPhone());
		existingColis.setEmailExpediteur(colisRequestDto.getSenderEmail());
		existingColis.setVilleOrigine(colisRequestDto.getOriginCity());
		existingColis.setQuartierExpedition(colisRequestDto.getOriginNeighborhood());
		existingColis.setNomDestinataire(colisRequestDto.getReceiverName());
		existingColis.setNumeroDestinataire(colisRequestDto.getReceiverPhone());
		existingColis.setEmailDestinataire(colisRequestDto.getReceiverEmail());
		existingColis.setVilleDeDestination(colisRequestDto.getDestinationCity());
		existingColis.setQuartierDestination(colisRequestDto.getDestinationNeighborhood());

		if (colisRequestDto.getModePaiement() != null && !colisRequestDto.getModePaiement().isEmpty()) {
			existingColis.setModePaiement(colisRequestDto.getModePaiement());
		}
		if (colisRequestDto.getPaymentReference() != null && !colisRequestDto.getPaymentReference().isEmpty()) {
			existingColis.setReferencePaiement(colisRequestDto.getPaymentReference());
		}

		if (colisRequestDto.getAgenceId() != null &&
				(existingColis.getAgence() == null || !existingColis.getAgence().getId().equals(colisRequestDto.getAgenceId()))) {
			Agence agence = agenceRepository.findById(colisRequestDto.getAgenceId())
					.orElseThrow(() -> new NotFoundException("Agence non trouvée avec l'ID: " + colisRequestDto.getAgenceId()));
			existingColis.setAgence(agence);
		}

		if (colisRequestDto.getClientId() == null ||
				(existingColis.getClientExpediteur() != null && existingColis.getClientExpediteur().getId().equals(colisRequestDto.getClientId()))) {
			if (colisRequestDto.getClientId() == null && existingColis.getClientExpediteur() != null) {
				existingColis.setClientExpediteur(null);
				existingColis.setNomExpediteur(colisRequestDto.getSenderName());
				existingColis.setTelephoneExpediteur(colisRequestDto.getSenderPhone());
				existingColis.setEmailExpediteur(colisRequestDto.getSenderEmail());
			}
		} else {
			Client client = clientRepository.findById(colisRequestDto.getClientId())
					.orElseThrow(() -> new NotFoundException("Client non trouvé avec l'ID: " + colisRequestDto.getClientId()));
			existingColis.setClientExpediteur(client);
			existingColis.setNomExpediteur(null);
			existingColis.setTelephoneExpediteur(null);
			existingColis.setEmailExpediteur(null);
		}

		Colis updatedColis = colisRepository.save(existingColis);
		log.info("Colis mis à jour avec succès: {}", updatedColis.getTrackingNumber());
		return mapToDto(updatedColis);
	}

	@Override
	public ColisResponseDto updateColisForClient(Long colisId, Long clientId, ColisRequestDto colisRequestDto) {
		Colis existingColis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		if (existingColis.getClientExpediteur() == null || !existingColis.getClientExpediteur().getId().equals(clientId)) {
			throw new OperationNotAllowedException("Le client avec l'ID " + clientId + " n'est pas autorisé à modifier ce colis.");
		}

		existingColis.setDescription(colisRequestDto.getDescription());
		existingColis.setDimensions(colisRequestDto.getDimensions());

		Colis updatedColis = colisRepository.save(existingColis);
		log.info("Colis du client {} mis à jour avec succès: {}", clientId, updatedColis.getTrackingNumber());
		return mapToDto(updatedColis);
	}

	@Override
	public void deleteColis(Long id) {
		Colis colis = colisRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + id));
		colisRepository.delete(colis);
		log.info("Colis avec l'ID {} supprimé avec succès.", id);
	}

	@Override
	public void cancelColisForClient(Long colisId, Long clientId) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		if (colis.getClientExpediteur() == null || !colis.getClientExpediteur().getId().equals(clientId)) {
			throw new OperationNotAllowedException("Le client avec l'ID " + clientId + " n'est pas autorisé à annuler ce colis.");
		}

		if (colis.getStatut() == ColisStatus.ANNULE || colis.getStatut() == ColisStatus.LIVRE) {
			throw new ConflictException("Le colis ne peut pas être annulé car il a déjà le statut: " + colis.getStatut().name());
		}

		colis.setStatut(ColisStatus.ANNULE);
		colisRepository.save(colis);
		log.info("Colis avec l'ID {} annulé par le client avec l'ID {}.", colisId, clientId);
	}

	@Override
	public ColisResponseDto getColisByTrackingNumber(String trackingNumber) {
		Colis colis = colisRepository.findByTrackingNumber(trackingNumber)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec le numéro de suivi: " + trackingNumber));
		return mapToDto(colis);
	}

	@Override
	public ColisResponseDto assignColisToVoyage(Long colisId, Long voyageId) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));
		Voyage voyage = voyageRepository.findById(voyageId)
				.orElseThrow(() -> new NotFoundException("Voyage non trouvé avec l'ID: " + voyageId));

		if (!(colis.getStatut() == ColisStatus.PRET_A_ENVOYER ||
				colis.getStatut() == ColisStatus.ENREGISTRE ||
				colis.getStatut() == ColisStatus.PAYE)) {
			throw new BadRequestException("Le colis n'est pas dans un statut permettant l'assignation au voyage. Statut actuel: " + colis.getStatut());
		}

		colis.setAssignedVoyage(voyage);
		colis.setStatut(ColisStatus.EN_TRANSIT);
		colis.setDateExpedition(LocalDateTime.now());

		Colis updatedColis = colisRepository.save(colis);
		log.info("Colis {} assigné au voyage {}. Statut mis à jour à EN_TRANSIT.", colisId, voyageId);
		return mapToDto(updatedColis);
	}

	@Override
	public ColisResponseDto updateColisStatus(Long colisId, String newStatus) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		ColisStatus status;
		try {
			status = ColisStatus.valueOf(newStatus.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BadRequestException("Statut de colis invalide: " + newStatus);
		}

		if (colis.getStatut() == ColisStatus.LIVRE && status != ColisStatus.LIVRE) {
			throw new OperationNotAllowedException("Un colis livré ne peut pas changer de statut.");
		}

		if (colis.getStatut() == ColisStatus.ANNULE && status != ColisStatus.ANNULE) {
			throw new OperationNotAllowedException("Un colis annulé ne peut pas changer de statut.");
		}

		if (status == ColisStatus.EN_TRANSIT && colis.getDateExpedition() == null) {
			colis.setDateExpedition(LocalDateTime.now());
			log.info("Date d'expédition définie pour le colis {}.", colisId);
		} else if (status == ColisStatus.ARRIVE_AGENCE_DESTINATION && colis.getDateArriveeAgenceDestination() == null) {
			colis.setDateArriveeAgenceDestination(LocalDateTime.now());
			log.info("Date d'arrivée à l'agence de destination définie pour le colis {}.", colisId);
		} else if (status == ColisStatus.LIVRE && colis.getDateLivraisonReelle() == null) {
			colis.setDateLivraisonReelle(LocalDateTime.now());
			log.info("Date de livraison réelle définie pour le colis {}.", colisId);
			sendDeliveryConfirmationNotification(colisId);
		}

		colis.setStatut(status);
		Colis updatedColis = colisRepository.save(colis);
		log.info("Statut du colis {} mis à jour de {} à {}.", colisId, colis.getStatut().name(), newStatus);
		return mapToDto(updatedColis);
	}

	@Override
	public void sendSenderPickupNotification(Long colisId) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		if (colis.getStatut() != ColisStatus.PRET_A_ENVOYER && colis.getStatut() != ColisStatus.PRET_A_ETRE_RETIRE) {
			log.warn("Tentative d'envoi de notification de prise en charge expéditeur pour un colis qui n'est pas prêt. Statut actuel: {}", colis.getStatut());
			return;
		}

		String recipientEmail = colis.getClientExpediteur() != null && colis.getClientExpediteur().getEmail() != null
				? colis.getClientExpediteur().getEmail()
				: colis.getEmailExpediteur();

		String recipientName = colis.getClientExpediteur() != null && colis.getClientExpediteur().getNom() != null
				? colis.getClientExpediteur().getNom() + " " +
				(colis.getClientExpediteur().getPrenom() != null ? colis.getClientExpediteur().getPrenom() : "")
				: colis.getNomExpediteur();

		if (recipientEmail != null && !recipientEmail.isEmpty()) {
			String subject = "Votre colis CDSIR Agency est prêt à être expédié ! - " + colis.getTrackingNumber();
			String text = String.format("Cher(e) %s,\n\nNous vous informons que votre colis (Numéro de suivi : %s) est maintenant **prêt à être expédié** depuis notre agence de %s.\n\nDescription : %s\nVeuillez vous assurer qu'il est bien pris en charge pour le voyage.\n\nMerci de faire confiance à CDSIR Agency.\nL'équipe CDSIR Agency",
					recipientName, colis.getTrackingNumber(),
					colis.getAgence() != null ? colis.getAgence().getNomAgence() : "N/A",
					colis.getDescription());

			try {
				notificationService.sendEmail(recipientEmail, subject, text);
				log.info("E-mail de notification de prise en charge expéditeur envoyé à : {}", recipientEmail);
			} catch (Exception e) {
				log.error("Échec de l'envoi de l'e-mail de notification de prise en charge expéditeur à {}: {}", recipientEmail, e.getMessage());
			}
		} else {
			log.warn("Aucune adresse e-mail trouvée pour l'expéditeur du colis {} pour envoyer la notification de prise en charge.", colis.getTrackingNumber());
		}

		String senderPhoneNumber = colis.getClientExpediteur() != null && colis.getClientExpediteur().getTelephone() != null
				? colis.getClientExpediteur().getTelephone()
				: colis.getTelephoneExpediteur();

		if (senderPhoneNumber != null && !senderPhoneNumber.isEmpty()) {
			String smsText = String.format("CDSIR: Votre colis %s est pret a etre expedie de %s. Merci!",
					colis.getTrackingNumber(),
					colis.getAgence() != null ? colis.getAgence().getNomAgence() : "N/A");

			try {
				notificationService.sendSms(senderPhoneNumber, smsText);
				log.info("SMS de notification de prise en charge expéditeur envoyé à : {}", senderPhoneNumber);
			} catch (Exception e) {
				log.error("Échec de l'envoi du SMS de notification de prise en charge expéditeur à {}: {}", senderPhoneNumber, e.getMessage());
			}
		} else {
			log.warn("Aucun numéro de téléphone trouvé pour l'expéditeur du colis {} pour envoyer le SMS de prise en charge.", colis.getTrackingNumber());
		}
	}

	@Override
	public void sendRecipientReadyForPickupNotification(Long colisId) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		if (colis.getStatut() != ColisStatus.PRET_A_ETRE_RETIRE) {
			log.warn("Tentative d'envoi de notification de retrait destinataire pour un colis qui n'est pas prêt à être retiré. Statut actuel: {}", colis.getStatut());
			return;
		}

		String recipientEmail = colis.getEmailDestinataire();
		String recipientName = colis.getNomDestinataire();

		if (recipientEmail != null && !recipientEmail.isEmpty()) {
			String subject = "Votre colis CDSIR Agency est prêt à être retiré ! - " + colis.getTrackingNumber();
			String text = String.format("Cher(e) %s,\n\nNous vous informons que votre colis (Numéro de suivi : %s) envoyé par %s est maintenant **prêt à être retiré** à notre agence de %s.\n\nDescription : %s\nVeuillez vous munir d'une pièce d'identité valide pour le retrait.\n\nMerci de faire confiance à CDSIR Agency.\nL'équipe CDSIR Agency",
					recipientName, colis.getTrackingNumber(), colis.getNomExpediteur(),
					colis.getAgence() != null ? colis.getAgence().getNomAgence() : "N/A",
					colis.getDescription());

			try {
				notificationService.sendEmail(recipientEmail, subject, text);
				log.info("E-mail de notification de retrait destinataire envoyé à : {}", recipientEmail);
			} catch (Exception e) {
				log.error("Échec de l'envoi de l'e-mail de notification de retrait destinataire à {}: {}", recipientEmail, e.getMessage());
			}
		} else {
			log.warn("Aucune adresse e-mail trouvée pour le destinataire du colis {} pour envoyer la notification de retrait.", colis.getTrackingNumber());
		}

		String recipientPhoneNumber = colis.getNumeroDestinataire();
		if (recipientPhoneNumber != null && !recipientPhoneNumber.isEmpty()) {
			String smsText = String.format("CDSIR: Votre colis %s est pret a etre retire a l'agence de %s. Merci!",
					colis.getTrackingNumber(),
					colis.getAgence() != null ? colis.getAgence().getNomAgence() : "N/A");

			try {
				notificationService.sendSms(recipientPhoneNumber, smsText);
				log.info("SMS de notification de retrait destinataire envoyé à : {}", recipientPhoneNumber);
			} catch (Exception e) {
				log.error("Échec de l'envoi du SMS de notification de retrait destinataire à {}: {}", recipientPhoneNumber, e.getMessage());
			}
		} else {
			log.warn("Aucun numéro de téléphone trouvé pour le destinataire du colis {} pour envoyer le SMS de retrait.", colis.getTrackingNumber());
		}
	}

	@Override
	public void sendDeliveryConfirmationNotification(Long colisId) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		if (colis.getStatut() != ColisStatus.LIVRE) {
			throw new BadRequestException("Le colis n'a pas le statut 'LIVRE'. Impossible d'envoyer la confirmation de livraison.");
		}

		try {
			// Notification à l'expéditeur
			if (colis.getClientExpediteur() != null && colis.getClientExpediteur().getEmail() != null && !colis.getClientExpediteur().getEmail().isEmpty()) {
				String senderEmail = colis.getClientExpediteur().getEmail();
				String senderName = colis.getClientExpediteur().getNom() + " " +
						(colis.getClientExpediteur().getPrenom() != null ? colis.getClientExpediteur().getPrenom() : "");
				String senderSubject = "Votre colis envoyé via CDSIR Agency a été livré - " + colis.getTrackingNumber();
				String senderText = String.format("Cher(e) %s,\n\nNous vous confirmons que votre colis (Numéro de suivi : %s) envoyé à %s a été **livré** avec succès au destinataire.\n\nDescription : %s\nDate et heure de livraison : %s\n\nMerci de faire confiance à CDSIR Agency.\nL'équipe CDSIR Agency",
						senderName, colis.getTrackingNumber(), colis.getNomDestinataire(),
						colis.getDescription(),
						colis.getDateLivraisonReelle() != null ? colis.getDateLivraisonReelle().format(DATE_TIME_FORMATTER) : "N/A");

				notificationService.sendEmail(senderEmail, senderSubject, senderText);
				log.info("E-mail de confirmation de livraison envoyé à l'expéditeur (client enregistré): {}", senderEmail);
			} else if (colis.getEmailExpediteur() != null && !colis.getEmailExpediteur().isEmpty()) {
				String senderEmail = colis.getEmailExpediteur();
				String senderName = colis.getNomExpediteur();
				String senderSubject = "Votre colis envoyé via CDSIR Agency a été livré - " + colis.getTrackingNumber();
				String senderText = String.format("Cher(e) %s,\n\nNous vous confirmons que votre colis (Numéro de suivi : %s) envoyé à %s a été **livré** avec succès au destinataire.\n\nDescription : %s\nDate et heure de livraison : %s\n\nMerci de faire confiance à CDSIR Agency.\nL'équipe CDSIR Agency",
						senderName, colis.getTrackingNumber(), colis.getNomDestinataire(),
						colis.getDescription(),
						colis.getDateLivraisonReelle() != null ? colis.getDateLivraisonReelle().format(DATE_TIME_FORMATTER) : "N/A");

				notificationService.sendEmail(senderEmail, senderSubject, senderText);
				log.info("E-mail de confirmation de livraison envoyé à l'expéditeur (client invité): {}", senderEmail);
			} else {
				log.warn("Aucune adresse e-mail trouvée pour l'expéditeur du colis {} pour envoyer la confirmation de livraison.", colis.getTrackingNumber());
			}

			// Notification au destinataire
			if (colis.getEmailDestinataire() != null && !colis.getEmailDestinataire().isEmpty()) {
				String recipientEmail = colis.getEmailDestinataire();
				String recipientName = colis.getNomDestinataire();
				String recipientSubject = "Votre colis de CDSIR Agency a été livré - " + colis.getTrackingNumber();
				String recipientText = String.format("Cher(e) %s,\n\nNous vous confirmons que votre colis (Numéro de suivi : %s) envoyé par %s a été **livré** avec succès.\n\nDescription : %s\nDate et heure de livraison : %s\n\nMerci de faire confiance à CDSIR Agency.\nL'équipe CDSIR Agency",
						recipientName, colis.getTrackingNumber(), colis.getNomExpediteur(),
						colis.getDescription(),
						colis.getDateLivraisonReelle() != null ? colis.getDateLivraisonReelle().format(DATE_TIME_FORMATTER) : "N/A");

				notificationService.sendEmail(recipientEmail, recipientSubject, recipientText);
				log.info("E-mail de confirmation de livraison envoyé au destinataire: {}", recipientEmail);
			} else {
				log.warn("Aucune adresse e-mail trouvée pour le destinataire du colis {} pour envoyer la confirmation de livraison.", colis.getTrackingNumber());
			}

			// SMS à l'expéditeur
			if (colis.getTelephoneExpediteur() != null && !colis.getTelephoneExpediteur().isEmpty()) {
				String smsSenderText = String.format("CDSIR: Votre colis %s destine a %s a ete livre avec succes. Merci!",
						colis.getTrackingNumber(), colis.getNomDestinataire());
				notificationService.sendSms(colis.getTelephoneExpediteur(), smsSenderText);
				log.info("SMS de confirmation de livraison envoyé à l'expéditeur: {}", colis.getTelephoneExpediteur());
			}

			// SMS au destinataire
			if (colis.getNumeroDestinataire() != null && !colis.getNumeroDestinataire().isEmpty()) {
				String smsRecipientText = String.format("CDSIR: Votre colis %s de %s a ete livre avec succes. Merci!",
						colis.getTrackingNumber(), colis.getNomExpediteur());
				notificationService.sendSms(colis.getNumeroDestinataire(), smsRecipientText);
				log.info("SMS de confirmation de livraison envoyé au destinataire: {}", colis.getNumeroDestinataire());
			}
		} catch (Exception e) {
			log.error("Échec de l'envoi des notifications de confirmation de livraison pour le colis {}: {}", colisId, e.getMessage());
			throw new RuntimeException("Échec de l'envoi des notifications de confirmation de livraison", e);
		}
	}

	@Override
	public List<ColisResponseDto> getColisByStatus(String status) {
		ColisStatus colisStatus;
		try {
			colisStatus = ColisStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BadRequestException("Statut de colis invalide: " + status);
		}

		return colisRepository.findByStatut(colisStatus).stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ColisResponseDto> getColisByVoyageId(Long voyageId) {
		return colisRepository.findByAssignedVoyageId(voyageId).stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ColisResponseDto> searchColis(String keyword) {
		List<Colis> colisList = colisRepository.findByTrackingNumberContainingIgnoreCaseOrNomDestinataireContainingIgnoreCaseOrNomExpediteurContainingIgnoreCase(
				keyword, keyword, keyword);

		return colisList.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public ColisResponseDto updateColisDeliveryDate(Long colisId, String newDeliveryDate) {
		Colis colis = colisRepository.findById(colisId)
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec l'ID: " + colisId));

		try {
			LocalDateTime deliveryDate = LocalDateTime.parse(newDeliveryDate, DATE_TIME_FORMATTER);
			colis.setDateLivraisonPrevue(deliveryDate);
			Colis updatedColis = colisRepository.save(colis);
			log.info("Date de livraison prévue mise à jour pour le colis {}: {}", colisId, newDeliveryDate);
			return mapToDto(updatedColis);
		} catch (DateTimeParseException e) {
			throw new BadRequestException("Format de date invalide. Utilisez le format dd/MM/yyyy HH:mm");
		}
	}

	@Override
	public ColisResponseDto confirmColisPayment(String paymentReference) {
		Colis colis = colisRepository.findByReferencePaiement(paymentReference)
				.stream()
				.findFirst()
				.orElseThrow(() -> new NotFoundException("Colis non trouvé avec la référence de paiement : " + paymentReference));

		if (!ColisStatus.EN_ATTENTE_PAIEMENT.equals(colis.getStatut())) {
			throw new OperationNotAllowedException("Le paiement du colis " + colis.getTrackingNumber() + " n'est pas en attente de confirmation.");
		}

		boolean paymentConfirmed = false;
		if ("MTN_MONEY".equalsIgnoreCase(colis.getModePaiement())) {
			paymentConfirmed = verifyMtnPayment(paymentReference);
		} else if ("ORANGE_MONEY".equalsIgnoreCase(colis.getModePaiement())) {
			paymentConfirmed = verifyOrangePayment(paymentReference);
		} else {
			throw new BadRequestException("Mode de paiement non pris en charge pour la validation : " + colis.getModePaiement());
		}

		if (!paymentConfirmed) {
			throw new BadRequestException("La validation du paiement a échoué pour la référence : " + paymentReference);
		}

		colis.setStatut(ColisStatus.PAYE);
		Colis updatedColis = colisRepository.save(colis);

		// Envoyer une notification de confirmation de paiement
		notificationService.sendEmail(
				updatedColis.getEmailExpediteur(),
				"Confirmation de paiement de colis",
				"Cher(e) " + updatedColis.getNomExpediteur() + ",\nVotre paiement pour le colis #" + updatedColis.getTrackingNumber() + " a été confirmé avec succès. Le colis est maintenant prêt à être traité."
		);
		notificationService.sendSms(
				updatedColis.getTelephoneExpediteur(),
				"Votre paiement pour le colis #" + updatedColis.getTrackingNumber() + " a été confirmé. Colis prêt."
		);

		return mapToDto(updatedColis);
	}

	private boolean initiateMtnPayment(String phoneNumber, double amount, String paymentReference) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(mtnApiKey);

		String requestBody = String.format("{\"amount\": \"%s\", \"currency\": \"XAF\", \"externalId\": \"%s\", \"payer\": {\"partyIdType\": \"MSISDN\", \"partyId\": \"%s\"}, \"payerMessage\": \"Paiement colis\", \"payeeNote\": \"Colis-%s\", \"callbackUrl\": \"%s\"}",
				String.valueOf(amount), paymentReference, phoneNumber, paymentReference, mtnCallbackUrl);

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(mtnMoneyApiUrl + "/v1_0/requesttopay", HttpMethod.POST, entity, String.class);
			log.info("MTN Payment initiation response for {}: {}", paymentReference, response.getStatusCode());
			return response.getStatusCode().is2xxSuccessful();
		} catch (Exception e) {
			log.error("Erreur lors de l'initiation du paiement MTN Money pour {}: {}", paymentReference, e.getMessage());
			return false;
		}
	}

	private boolean verifyMtnPayment(String paymentReference) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(mtnApiKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(mtnMoneyApiUrl + "/v1_0/requesttopay/" + paymentReference, HttpMethod.GET, entity, String.class);
			log.info("MTN Payment verification response for {}: {}", paymentReference, response.getStatusCode());

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				JsonNode root = objectMapper.readTree(response.getBody());
				String status = root.path("status").asText();
				return "SUCCESSFUL".equalsIgnoreCase(status);
			}
			return false;
		} catch (Exception e) {
			log.error("Erreur lors de la vérification du paiement MTN Money pour {}: {}", paymentReference, e.getMessage());
			return false;
		}
	}

	private boolean initiateOrangePayment(String phoneNumber, double amount, String paymentReference) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Auth-Token", orangeApiSecret);

		String requestBody = String.format("{\"merchantId\": \"%s\", \"recipientPhoneNumber\": \"%s\", \"amount\": %s, \"currency\": \"XAF\", \"transactionRef\": \"%s\", \"callbackUrl\": \"%s\"}",
				orangeMerchantId, phoneNumber, amount, paymentReference, orangeCallbackUrl);

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(orangeMoneyApiUrl + "/initiate", HttpMethod.POST, entity, String.class);
			log.info("Orange Payment initiation response for {}: {}", paymentReference, response.getStatusCode());
			return response.getStatusCode().is2xxSuccessful();
		} catch (Exception e) {
			log.error("Erreur lors de l'initiation du paiement Orange Money pour {}: {}", paymentReference, e.getMessage());
			return false;
		}
	}

	private boolean verifyOrangePayment(String paymentReference) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Auth-Token", orangeApiSecret);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(orangeMoneyApiUrl + "/" + paymentReference, HttpMethod.GET, entity, String.class);
			log.info("Orange Payment verification response for {}: {}", paymentReference, response.getStatusCode());

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				JsonNode root = objectMapper.readTree(response.getBody());
				String status = root.path("status").asText();
				return "SUCCESS".equalsIgnoreCase(status);
			}
			return false;
		} catch (Exception e) {
			log.error("Erreur lors de la vérification du paiement Orange Money pour {}: {}", paymentReference, e.getMessage());
			return false;
		}
	}

	private String generateUniqueTrackingNumber() {
		String trackingNumber;
		do {
			trackingNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		} while(colisRepository.findByTrackingNumber(trackingNumber).isPresent());

		return trackingNumber;
	}

	private ColisResponseDto mapToDto(Colis colis) {
		ColisResponseDto dto = new ColisResponseDto();
		dto.setIdColis(colis.getId());
		dto.setDescription(colis.getDescription());
		dto.setWeight(colis.getWeight());
		dto.setDimensions(colis.getDimensions());
		dto.setEstimatedCost(colis.getEstimatedCost());
		dto.setTrackingNumber(colis.getTrackingNumber());
		dto.setStatut(colis.getStatut());
		dto.setDateEnregistrement(colis.getDateEnregistrement());
		dto.setDateExpedition(colis.getDateExpedition());
		dto.setDateArriveeAgenceDestination(colis.getDateArriveeAgenceDestination());
		dto.setDateLivraisonPrevue(colis.getDateLivraisonPrevue());
		dto.setDateLivraisonReelle(colis.getDateLivraisonReelle());
		dto.setNomDestinataire(colis.getNomDestinataire());
		dto.setNumeroDestinataire(colis.getNumeroDestinataire());
		dto.setEmailDestinataire(colis.getEmailDestinataire());
		dto.setVilleDeDestination(colis.getVilleDeDestination());
		dto.setQuartierDestination(colis.getQuartierDestination());
		dto.setModePaiement(colis.getModePaiement());
		dto.setPaymentReference(colis.getReferencePaiement());
		dto.setCodeValidation(colis.getCodeValidation());

		if (colis.getClientExpediteur() != null) {
			dto.setClientId(colis.getClientExpediteur().getId());
			dto.setSenderName(colis.getClientExpediteur().getNom() + " " +
					(colis.getClientExpediteur().getPrenom() != null ? colis.getClientExpediteur().getPrenom() : ""));
			dto.setSenderPhone(colis.getClientExpediteur().getTelephone());
			dto.setSenderEmail(colis.getClientExpediteur().getEmail());
		} else {
			dto.setSenderName(colis.getNomExpediteur());
			dto.setSenderPhone(colis.getTelephoneExpediteur());
			dto.setSenderEmail(colis.getEmailExpediteur());
		}

		dto.setVilleOrigine(colis.getVilleOrigine());
		dto.setQuartierExpedition(colis.getQuartierExpedition());

		if (colis.getAgence() != null) {
			dto.setAgenceId(colis.getAgence().getId());
			dto.setAgenceNom(colis.getAgence().getNomAgence());
		}

		if (colis.getAssignedVoyage() != null) {
			dto.setAssignedVoyageId(colis.getAssignedVoyage().getId());

			if (colis.getAssignedVoyage().getTrajet() != null && colis.getAssignedVoyage().getDateDepart() != null) {
				dto.setAssignedVoyageDescription(
						colis.getAssignedVoyage().getTrajet().getVilleDepart() + " -> " +
								colis.getAssignedVoyage().getTrajet().getVilleDestination() + ", " +
								colis.getAssignedVoyage().getDateDepart() + " " +
								(colis.getAssignedVoyage().getHoraire() != null ? colis.getAssignedVoyage().getHoraire().getHeureDepart() : ""));
			}

			if (colis.getAssignedVoyage().getVehicule() != null) {
				dto.setAssignedVoyageVehiculeImmatriculation(colis.getAssignedVoyage().getVehicule().getImmatriculation());
			}
		}

		return dto;
	}

	private Colis mapToEntity(ColisRequestDto dto) {
		Colis colis = new Colis();
		colis.setDescription(dto.getDescription());
		colis.setWeight(dto.getWeight());
		colis.setDimensions(dto.getDimensions());
		colis.setEstimatedCost(dto.getEstimatedCost());
		colis.setNomExpediteur(dto.getSenderName());
		colis.setTelephoneExpediteur(dto.getSenderPhone());
		colis.setEmailExpediteur(dto.getSenderEmail());
		colis.setVilleOrigine(dto.getOriginCity());
		colis.setQuartierExpedition(dto.getOriginNeighborhood());
		colis.setNomDestinataire(dto.getReceiverName());
		colis.setNumeroDestinataire(dto.getReceiverPhone());
		colis.setEmailDestinataire(dto.getReceiverEmail());
		colis.setVilleDeDestination(dto.getDestinationCity());
		colis.setQuartierDestination(dto.getDestinationNeighborhood());
		colis.setModePaiement(dto.getModePaiement());
		colis.setReferencePaiement(dto.getPaymentReference());
		colis.setDateLivraisonPrevue(dto.getPlannedDeliveryDate());
		return colis;
	}
}