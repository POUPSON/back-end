package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.dto.ReservationRequestDto;
import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.entity.Reservation;
import PFE.CDSIR_AGENCY.entity.Voyage;
import PFE.CDSIR_AGENCY.exception.BadRequestException;
import PFE.CDSIR_AGENCY.exception.ConflictException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.exception.OperationNotAllowedException;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import PFE.CDSIR_AGENCY.repository.ReservationRepository;
import PFE.CDSIR_AGENCY.repository.VoyageRepository;
import PFE.CDSIR_AGENCY.service.NotificationService;
import PFE.CDSIR_AGENCY.service.ReservationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

	private final ReservationRepository reservationRepository;
	private final VoyageRepository voyageRepository;
	private final ClientRepository clientRepository;
	private final NotificationService notificationService;
	private final RestTemplate restTemplate;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	// Payment constants
	public static final String MTN_MONEY = "MTN_MONEY";
	public static final String ORANGE_MONEY = "ORANGE_MONEY";
	public static final String MOBILE_MONEY = "MOBILE_MONEY";

	// Payment configuration
	@Value("${payment.mtn.api.url}")
	private String mtnMoneyApiUrl;

	@Value("${payment.mtn.merchant.code}")
	private String mtnMerchantCode;

	@Value("${payment.mtn.api.key}")
	private String mtnApiKey;

	@Value("${payment.orange.api.url}")
	private String orangeMoneyApiUrl;

	@Value("${payment.orange.merchant.id}")
	private String orangeMerchantId;

	@Value("${payment.orange.api.secret}")
	private String orangeApiSecret;

	public ReservationServiceImpl(ReservationRepository reservationRepository,
								  VoyageRepository voyageRepository,
								  ClientRepository clientRepository,
								  NotificationService notificationService,
								  RestTemplate restTemplate) {
		this.reservationRepository = reservationRepository;
		this.voyageRepository = voyageRepository;
		this.clientRepository = clientRepository;
		this.notificationService = notificationService;
		this.restTemplate = restTemplate;
	}

	@Override
	public Reservation createReservation(ReservationRequestDto reservationRequestDto) {
		// Validate voyage
		Voyage voyage = voyageRepository.findById(reservationRequestDto.getVoyageId())
				.orElseThrow(() -> new NotFoundException("Voyage not found with ID: " + reservationRequestDto.getVoyageId()));

		// Check seat availability
		if (reservationRepository.existsByVoyageIdAndSiegeReserver(voyage.getId(), reservationRequestDto.getSiegeReserver())) {
			throw new ConflictException("Seat " + reservationRequestDto.getSiegeReserver() + " is already reserved for this voyage.");
		}

		// Build reservation
		Reservation reservation = new Reservation();
		reservation.setVoyage(voyage);
		reservation.setSiegeReserver(reservationRequestDto.getSiegeReserver());
		reservation.setClasse(reservationRequestDto.getClasse());
		reservation.setModePaiement(reservationRequestDto.getModePaiement());
		reservation.setMontant(reservationRequestDto.getMontant());
		reservation.setReservationDate(LocalDateTime.now());
		reservation.setStatut("EN_ATTENTE");

		// Handle client info
		if (reservationRequestDto.getClientId() != null) {
			Client client = clientRepository.findById(reservationRequestDto.getClientId())
					.orElseThrow(() -> new NotFoundException("Client not found with ID: " + reservationRequestDto.getClientId()));
			reservation.setClient(client);
		} else {
			if (reservationRequestDto.getClientNom() == null || reservationRequestDto.getClientEmail() == null || reservationRequestDto.getClientTelephone() == null) {
				throw new BadRequestException("Client information (name, email, phone) is required for guest clients.");
			}
			reservation.setClientNom(reservationRequestDto.getClientNom());
			reservation.setClientPrenom(reservationRequestDto.getClientPrenom());
			reservation.setClientEmail(reservationRequestDto.getClientEmail());
			reservation.setClientTelephone(reservationRequestDto.getClientTelephone());
		}

		// Handle mobile money payments
		if (MTN_MONEY.equalsIgnoreCase(reservationRequestDto.getModePaiement()) ||
				ORANGE_MONEY.equalsIgnoreCase(reservationRequestDto.getModePaiement())) {

			if (reservationRequestDto.getPaymentReference() == null || reservationRequestDto.getPaymentReference().isEmpty()) {
				throw new BadRequestException("Payment reference is required for Mobile Money payments.");
			}

			boolean paymentInitiated = false;
			if (MTN_MONEY.equalsIgnoreCase(reservationRequestDto.getModePaiement())) {
				paymentInitiated = initiateMtnPayment(reservationRequestDto);
			} else {
				paymentInitiated = initiateOrangePayment(reservationRequestDto);
			}

			if (!paymentInitiated) {
				throw new BadRequestException("Failed to initiate Mobile Money payment. Please try again.");
			}

			reservation.setPaymentReference(reservationRequestDto.getPaymentReference());
		}

		// Generate unique validation code
		String validationCode;
		do {
			validationCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		} while (reservationRepository.findByCodeValidation(validationCode).isPresent());
		reservation.setCodeValidation(validationCode);

		// Save and send confirmation
		Reservation savedReservation = reservationRepository.save(reservation);
		sendReservationConfirmationEmail(savedReservation);
		return savedReservation;
	}

	private boolean initiateMtnPayment(ReservationRequestDto request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + mtnApiKey);

			String requestBody = String.format(
					"{\"merchantCode\":\"%s\",\"phoneNumber\":\"%s\",\"amount\":%.2f,\"reference\":\"%s\"}",
					mtnMerchantCode, request.getClientTelephone(), request.getMontant(), request.getPaymentReference());

			HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					mtnMoneyApiUrl, HttpMethod.POST, entity, String.class);

			return response.getStatusCode() == HttpStatus.OK;
		} catch (Exception e) {
			log.error("MTN Money payment initiation failed", e);
			return false;
		}
	}

	private boolean initiateOrangePayment(ReservationRequestDto request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Merchant-ID", orangeMerchantId);
			headers.set("X-Auth-Token", orangeApiSecret);

			String requestBody = String.format(
					"{\"recipientPhone\":\"%s\",\"amount\":%.2f,\"transactionRef\":\"%s\"}",
					request.getClientTelephone(), request.getMontant(), request.getPaymentReference());

			HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					orangeMoneyApiUrl, HttpMethod.POST, entity, String.class);

			return response.getStatusCode() == HttpStatus.OK;
		} catch (Exception e) {
			log.error("Orange Money payment initiation failed", e);
			return false;
		}
	}

	@Override
	public Reservation createReservationForClient(Long clientId, Long voyageId, ReservationRequestDto reservationRequestDto) {
		if (reservationRequestDto.getClientId() != null && !reservationRequestDto.getClientId().equals(clientId)) {
			throw new BadRequestException("Client ID in request doesn't match authenticated client ID.");
		}
		return createReservation(reservationRequestDto);
	}

	@Override
	public Reservation getReservationById(Long id) {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Reservation not found with ID: " + id));
	}

	@Override
	public Reservation getReservationByIdAndClientId(Long reservationId, Long clientId) {
		Reservation reservation = getReservationById(reservationId);
		if (reservation.getClient() != null && !reservation.getClient().getId().equals(clientId)) {
			throw new OperationNotAllowedException("You are not authorized to view this reservation.");
		}
		return reservation;
	}

	@Override
	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}

	@Override
	public List<Reservation> getReservationsByClientId(Long clientId) {
		return reservationRepository.findByClientId(clientId);
	}

	@Override
	public Reservation updateReservation(Long id, ReservationRequestDto reservationRequestDto) {
		Reservation existingReservation = getReservationById(id);

		// Update voyage if changed
		if (!existingReservation.getVoyage().getId().equals(reservationRequestDto.getVoyageId())) {
			Voyage newVoyage = voyageRepository.findById(reservationRequestDto.getVoyageId())
					.orElseThrow(() -> new NotFoundException("New voyage not found with ID: " + reservationRequestDto.getVoyageId()));
			existingReservation.setVoyage(newVoyage);
		}

		// Check seat availability
		if ((!existingReservation.getSiegeReserver().equals(reservationRequestDto.getSiegeReserver()) ||
				!existingReservation.getVoyage().getId().equals(reservationRequestDto.getVoyageId())) &&
				reservationRepository.existsByVoyageIdAndSiegeReserver(existingReservation.getVoyage().getId(), reservationRequestDto.getSiegeReserver())) {
			throw new ConflictException("Seat " + reservationRequestDto.getSiegeReserver() + " is already reserved for this voyage.");
		}

		// Update basic info
		existingReservation.setSiegeReserver(reservationRequestDto.getSiegeReserver());
		existingReservation.setClasse(reservationRequestDto.getClasse());
		existingReservation.setModePaiement(reservationRequestDto.getModePaiement());
		existingReservation.setMontant(reservationRequestDto.getMontant());

		// Update client info
		if (reservationRequestDto.getClientId() != null) {
			Client client = clientRepository.findById(reservationRequestDto.getClientId())
					.orElseThrow(() -> new NotFoundException("Client not found with ID: " + reservationRequestDto.getClientId()));
			existingReservation.setClient(client);
			existingReservation.setClientNom(null);
			existingReservation.setClientPrenom(null);
			existingReservation.setClientEmail(null);
			existingReservation.setClientTelephone(null);
		} else {
			if (reservationRequestDto.getClientNom() == null || reservationRequestDto.getClientEmail() == null || reservationRequestDto.getClientTelephone() == null) {
				throw new BadRequestException("Client information (name, email, phone) is required for guest clients.");
			}
			existingReservation.setClient(null);
			existingReservation.setClientNom(reservationRequestDto.getClientNom());
			existingReservation.setClientPrenom(reservationRequestDto.getClientPrenom());
			existingReservation.setClientEmail(reservationRequestDto.getClientEmail());
			existingReservation.setClientTelephone(reservationRequestDto.getClientTelephone());
		}

		// Update payment info
		if (MTN_MONEY.equalsIgnoreCase(reservationRequestDto.getModePaiement()) ||
				ORANGE_MONEY.equalsIgnoreCase(reservationRequestDto.getModePaiement())) {

			if (reservationRequestDto.getPaymentReference() == null || reservationRequestDto.getPaymentReference().isEmpty()) {
				throw new BadRequestException("Payment reference is required for Mobile Money payments.");
			}
			existingReservation.setPaymentReference(reservationRequestDto.getPaymentReference());
		} else {
			existingReservation.setPaymentReference(null);
		}

		return reservationRepository.save(existingReservation);
	}

	@Override
	public Reservation updateReservationForClient(Long reservationId, Long clientId, ReservationRequestDto reservationRequestDto) {
		Reservation existingReservation = getReservationByIdAndClientId(reservationId, clientId);
		if (reservationRequestDto.getClientId() != null && !reservationRequestDto.getClientId().equals(clientId)) {
			throw new OperationNotAllowedException("You cannot modify another client's reservation.");
		}

		existingReservation.setSiegeReserver(reservationRequestDto.getSiegeReserver());
		existingReservation.setClasse(reservationRequestDto.getClasse());
		existingReservation.setModePaiement(reservationRequestDto.getModePaiement());
		existingReservation.setMontant(reservationRequestDto.getMontant());

		if (MTN_MONEY.equalsIgnoreCase(reservationRequestDto.getModePaiement()) ||
				ORANGE_MONEY.equalsIgnoreCase(reservationRequestDto.getModePaiement())) {

			if (reservationRequestDto.getPaymentReference() == null || reservationRequestDto.getPaymentReference().isEmpty()) {
				throw new BadRequestException("Payment reference is required for Mobile Money payments.");
			}
			existingReservation.setPaymentReference(reservationRequestDto.getPaymentReference());
		} else {
			existingReservation.setPaymentReference(null);
		}

		return reservationRepository.save(existingReservation);
	}

	@Override
	public void deleteReservation(Long id) {
		Reservation reservation = getReservationById(id);
		reservationRepository.delete(reservation);
	}

	@Override
	public void cancelReservationForClient(Long reservationId, Long clientId) {
		Reservation reservation = getReservationByIdAndClientId(reservationId, clientId);
		if (!"EN_ATTENTE".equalsIgnoreCase(reservation.getStatut()) && !"VALIDEE".equalsIgnoreCase(reservation.getStatut())) {
			throw new OperationNotAllowedException("Reservation can only be cancelled when status is 'EN_ATTENTE' or 'VALIDEE'.");
		}
		reservation.setStatut("ANNULEE");
		reservationRepository.save(reservation);
	}

	@Override
	public Reservation validateReservation(String validationCode) {
		Reservation reservation = reservationRepository.findByCodeValidation(validationCode)
				.orElseThrow(() -> new NotFoundException("Invalid or not found validation code."));

		if ("VALIDEE".equalsIgnoreCase(reservation.getStatut())) {
			throw new ConflictException("This reservation is already validated.");
		}
		if ("ANNULEE".equalsIgnoreCase(reservation.getStatut())) {
			throw new ConflictException("This reservation has been cancelled.");
		}

		reservation.setStatut("VALIDEE");
		Reservation savedReservation = reservationRepository.save(reservation);
		sendReservationValidationEmail(savedReservation);
		return savedReservation;
	}

	@Override
	public List<Reservation> getReservationsByVoyageId(Long voyageId) {
		return reservationRepository.findByVoyageId(voyageId);
	}

	@Override
	public List<Reservation> getReservationsByStatus(String status) {
		if (status == null || status.trim().isEmpty()) {
			throw new BadRequestException("Status cannot be empty");
		}
		return reservationRepository.findByStatutIgnoreCase(status);
	}

	@Override
	public void verifyReservationOwnership(Long reservationId, Long clientId) {
		Reservation reservation = getReservationById(reservationId);
		if (reservation.getClient() == null || !reservation.getClient().getId().equals(clientId)) {
			throw new OperationNotAllowedException("You are not authorized to access this reservation");
		}
	}

	@Override
	public Reservation confirmReservationPayment(Long reservationId) {
		Reservation reservation = getReservationById(reservationId);
		if ("PAYE".equalsIgnoreCase(reservation.getStatut())) {
			throw new ConflictException("This reservation is already paid");
		}

		// Verify payment with provider
		if (MTN_MONEY.equalsIgnoreCase(reservation.getModePaiement())) {
			if (!verifyMtnPayment(reservation.getPaymentReference())) {
				throw new BadRequestException("MTN Money payment not confirmed");
			}
		} else if (ORANGE_MONEY.equalsIgnoreCase(reservation.getModePaiement())) {
			if (!verifyOrangePayment(reservation.getPaymentReference())) {
				throw new BadRequestException("Orange Money payment not confirmed");
			}
		}

		reservation.setStatut("PAYE");
		return reservationRepository.save(reservation);
	}

	private boolean verifyMtnPayment(String paymentReference) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + mtnApiKey);

			String url = String.format("%s/verify?reference=%s", mtnMoneyApiUrl, paymentReference);
			ResponseEntity<String> response = restTemplate.exchange(
					url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

			return response.getStatusCode() == HttpStatus.OK;
		} catch (Exception e) {
			log.error("MTN Money payment verification failed", e);
			return false;
		}
	}

	private boolean verifyOrangePayment(String paymentReference) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-Merchant-ID", orangeMerchantId);
			headers.set("X-Auth-Token", orangeApiSecret);

			String url = String.format("%s/transactions/%s", orangeMoneyApiUrl, paymentReference);
			ResponseEntity<String> response = restTemplate.exchange(
					url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

			return response.getStatusCode() == HttpStatus.OK;
		} catch (Exception e) {
			log.error("Orange Money payment verification failed", e);
			return false;
		}
	}

	@Override
	public List<Reservation> searchReservations(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return getAllReservations();
		}
		return reservationRepository.searchReservations(keyword.toLowerCase());
	}

	@Override
	public Reservation updateReservationStatus(Long reservationId, String newStatus) {
		Reservation reservation = getReservationById(reservationId);

		if (newStatus == null || newStatus.trim().isEmpty()) {
			throw new BadRequestException("Status cannot be empty");
		}
		if ("ANNULEE".equalsIgnoreCase(reservation.getStatut())) {
			throw new OperationNotAllowedException("Cannot modify a cancelled reservation");
		}

		reservation.setStatut(newStatus.toUpperCase());
		Reservation updatedReservation = reservationRepository.save(reservation);

		if ("VALIDEE".equalsIgnoreCase(newStatus)) {
			sendReservationValidationEmail(updatedReservation);
		}

		return updatedReservation;
	}

	private void sendReservationConfirmationEmail(Reservation reservation) {
		try {
			String clientEmail = reservation.getClient() != null ?
					reservation.getClient().getEmail() : reservation.getClientEmail();

			if (clientEmail == null || clientEmail.isEmpty()) {
				log.warn("Cannot send confirmation email: missing email for reservation {}", reservation.getId());
				return;
			}

			String subject = "Reservation Confirmation";
			String content = buildConfirmationEmailContent(reservation);
			notificationService.sendEmail(clientEmail, subject, content);
			log.info("Confirmation email sent to {}", clientEmail);
		} catch (Exception e) {
			log.error("Failed to send confirmation email", e);
		}
	}

	private void sendReservationValidationEmail(Reservation reservation) {
		try {
			String clientEmail = reservation.getClient() != null ?
					reservation.getClient().getEmail() : reservation.getClientEmail();

			if (clientEmail == null || clientEmail.isEmpty()) {
				log.warn("Cannot send validation email: missing email for reservation {}", reservation.getId());
				return;
			}

			String subject = "Reservation Validation";
			String content = buildValidationEmailContent(reservation);
			notificationService.sendEmail(clientEmail, subject, content);
			log.info("Validation email sent to {}", clientEmail);
		} catch (Exception e) {
			log.error("Failed to send validation email", e);
		}
	}

	private String buildConfirmationEmailContent(Reservation reservation) {
		StringBuilder content = new StringBuilder();
		content.append("Dear ");

		if (reservation.getClient() != null) {
			content.append(reservation.getClient().getNom());
			if (reservation.getClient().getPrenom() != null) {
				content.append(" ").append(reservation.getClient().getPrenom());
			}
		} else {
			content.append(reservation.getClientNom());
			if (reservation.getClientPrenom() != null) {
				content.append(" ").append(reservation.getClientPrenom());
			}
		}

		content.append(",\n\n");
		content.append("We have received your reservation with the following details:\n\n");

		if (reservation.getVoyage() != null) {
			content.append("Trip: ").append(reservation.getVoyage().getTrajet().getVilleDepart())
					.append(" -> ").append(reservation.getVoyage().getTrajet().getVilleDestination()).append("\n");
			content.append("Date: ").append(reservation.getVoyage().getDateDepart().format(DATE_TIME_FORMATTER)).append("\n");
		}

		content.append("Seat: ").append(reservation.getSiegeReserver()).append("\n");
		content.append("Class: ").append(reservation.getClasse()).append("\n");
		content.append("Amount: ").append(String.format("%.2f FCFA", reservation.getMontant())).append("\n");
		content.append("Payment Method: ").append(reservation.getModePaiement()).append("\n");
		content.append("Status: ").append(reservation.getStatut()).append("\n");
		content.append("Reservation Code: ").append(reservation.getCodeValidation()).append("\n\n");

		if (reservation.getPaymentReference() != null) {
			content.append("Payment Reference: ").append(reservation.getPaymentReference()).append("\n\n");
		}

		content.append("Thank you for your trust.\n");
		content.append("CDSIR Agency Team");

		return content.toString();
	}

	private String buildValidationEmailContent(Reservation reservation) {
		StringBuilder content = new StringBuilder();
		content.append("Dear ");

		if (reservation.getClient() != null) {
			content.append(reservation.getClient().getNom());
			if (reservation.getClient().getPrenom() != null) {
				content.append(" ").append(reservation.getClient().getPrenom());
			}
		} else {
			content.append(reservation.getClientNom());
			if (reservation.getClientPrenom() != null) {
				content.append(" ").append(reservation.getClientPrenom());
			}
		}

		content.append(",\n\n");
		content.append("We are pleased to inform you that your reservation #");
		content.append(reservation.getCodeValidation()).append(" has been validated.\n\n");

		content.append("Reservation details:\n");
		if (reservation.getVoyage() != null) {
			content.append("- Trip: ").append(reservation.getVoyage().getTrajet().getVilleDepart())
					.append(" -> ").append(reservation.getVoyage().getTrajet().getVilleDestination()).append("\n");
			content.append("- Date: ").append(reservation.getVoyage().getDateDepart().format(DATE_TIME_FORMATTER)).append("\n");
		}
		content.append("- Seat: ").append(reservation.getSiegeReserver()).append("\n");
		content.append("- Class: ").append(reservation.getClasse()).append("\n");
		content.append("- Payment Method: ").append(reservation.getModePaiement()).append("\n\n");

		if (reservation.getPaymentReference() != null) {
			content.append("- Payment Reference: ").append(reservation.getPaymentReference()).append("\n\n");
		}

		content.append("We wish you a pleasant journey!\n\n");
		content.append("CDSIR Agency Team");

		return content.toString();
	}
}