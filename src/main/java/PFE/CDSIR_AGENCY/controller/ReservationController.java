//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.dto.ReservationRequestDto;
import PFE.CDSIR_AGENCY.dto.ReservationResponseDto;
import PFE.CDSIR_AGENCY.entity.Reservation;
import PFE.CDSIR_AGENCY.service.ClientService;
import PFE.CDSIR_AGENCY.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/reservations"})
@Tag(
		name = "Gestion Réservations (Client)",
		description = "API pour la gestion des réservations par les clients"
)
public class ReservationController {
	private final ReservationService reservationService;
	private final ClientService clientService;
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Operation(
			summary = "Créer une réservation pour le client connecté sur un voyage spécifique"
	)
	@PostMapping({"/voyage/{voyageId}"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<ReservationResponseDto> createReservationForAuthenticatedClient(@PathVariable Long voyageId, @RequestBody @Valid ReservationRequestDto reservationRequestDto) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		Reservation createdReservation = this.reservationService.createReservationForClient(currentClientId, voyageId, reservationRequestDto);
		return new ResponseEntity(this.convertToDto(createdReservation), HttpStatus.CREATED);
	}

	@Operation(
			summary = "Obtenir une réservation par son ID (pour le client connecté)"
	)
	@GetMapping({"/{id}"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<ReservationResponseDto> getReservationByIdForClient(@PathVariable Long id) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		Reservation reservation = this.reservationService.getReservationByIdAndClientId(id, currentClientId);
		return ResponseEntity.ok(this.convertToDto(reservation));
	}

	@Operation(
			summary = "Obtenir toutes les réservations du client connecté"
	)
	@GetMapping({"/my-history"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<List<ReservationResponseDto>> getMyReservations() {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		List<Reservation> reservations = this.reservationService.getReservationsByClientId(currentClientId);
		return ResponseEntity.ok((List)reservations.stream().map(this::convertToDto).collect(Collectors.toList()));
	}

	@Operation(
			summary = "Mettre à jour une réservation existante (pour le client connecté)"
	)
	@PutMapping({"/{id}"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<ReservationResponseDto> updateReservationForClient(@PathVariable Long id, @RequestBody @Valid ReservationRequestDto reservationRequestDto) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		Reservation updatedReservation = this.reservationService.updateReservationForClient(id, currentClientId, reservationRequestDto);
		return ResponseEntity.ok(this.convertToDto(updatedReservation));
	}

	@Operation(
			summary = "Annuler une réservation (pour le client connecté)"
	)
	@PatchMapping({"/{id}/cancel"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Void> cancelReservationForClient(@PathVariable Long id) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		this.reservationService.cancelReservationForClient(id, currentClientId);
		return ResponseEntity.noContent().build();
	}

	@Operation(
			summary = "Valider une réservation avec un code (utilisé principalement par l'agence/agent)"
	)
	@GetMapping({"/validate/{codeValidation}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<ReservationResponseDto> validateReservation(@PathVariable String codeValidation) {
		Reservation validatedReservation = this.reservationService.validateReservation(codeValidation);
		return ResponseEntity.ok(this.convertToDto(validatedReservation));
	}

	private ReservationResponseDto convertToDto(Reservation reservation) {
		ReservationResponseDto dto = new ReservationResponseDto();
		dto.setIdReservation(reservation.getId());
		dto.setDateReservation(reservation.getReservationDate());
		dto.setSiegeReserver(reservation.getSiegeReserver());
		dto.setClasse(reservation.getClasse());
		dto.setStatut(reservation.getStatut());
		dto.setCodeValidation(reservation.getCodeValidation());
		dto.setPaymentReference(reservation.getPaymentReference());
		dto.setMontant(reservation.getMontant());
		if (reservation.getClient() != null) {
			dto.setClientId(reservation.getClient().getId());
			dto.setClientNom(reservation.getClient().getNom() + " " + (reservation.getClient().getPrenom() != null ? reservation.getClient().getPrenom() : ""));
		}

		if (reservation.getVoyage() != null) {
			dto.setVoyageId(reservation.getVoyage().getId());
			if (reservation.getVoyage().getTrajet() != null && reservation.getVoyage().getDateDepart() != null) {
				dto.setVoyageDescription(reservation.getVoyage().getTrajet().getVilleDepart() + " -> " + reservation.getVoyage().getTrajet().getVilleDestination() + ", " + reservation.getVoyage().getDateDepart().format(DATE_TIME_FORMATTER));
			}

			if (reservation.getVoyage().getVehicule() != null) {
				dto.setVehiculeImmatriculation(reservation.getVoyage().getVehicule().getImmatriculation());
			}
		}

		return dto;
	}

	@Generated
	public ReservationController(final ReservationService reservationService, final ClientService clientService) {
		this.reservationService = reservationService;
		this.clientService = clientService;
	}
}
