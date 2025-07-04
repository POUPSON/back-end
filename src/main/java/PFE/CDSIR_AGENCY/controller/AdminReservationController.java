//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.dto.ReservationRequestDto;
import PFE.CDSIR_AGENCY.dto.ReservationResponseDto;
import PFE.CDSIR_AGENCY.entity.Reservation;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/admin/reservations"})
@Tag(
        name = "Gestion Réservations (Admin)",
        description = "API pour la gestion des réservations par les administrateurs et agents"
)
public class AdminReservationController {
    private final ReservationService reservationService;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Operation(
            summary = "Créer une réservation (Admin/Agent)"
    )
    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservationForAdmin(@RequestBody @Valid ReservationRequestDto reservationRequestDto) {
        Reservation createdReservation = this.reservationService.createReservation(reservationRequestDto);
        return new ResponseEntity(this.convertToDto(createdReservation), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtenir toutes les réservations (Admin/Agent)"
    )
    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        List<Reservation> reservations = this.reservationService.getAllReservations();
        return ResponseEntity.ok((List)reservations.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @Operation(
            summary = "Obtenir une réservation par ID (Admin/Agent)"
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id) {
        Reservation reservation = this.reservationService.getReservationById(id);
        return ResponseEntity.ok(this.convertToDto(reservation));
    }

    @Operation(
            summary = "Mettre à jour une réservation par ID (Admin/Agent)"
    )
    @PutMapping({"/{id}"})
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable Long id, @RequestBody @Valid ReservationRequestDto reservationRequestDto) {
        Reservation updatedReservation = this.reservationService.updateReservation(id, reservationRequestDto);
        return ResponseEntity.ok(this.convertToDto(updatedReservation));
    }

    @Operation(
            summary = "Supprimer une réservation par ID (Admin/Agent)"
    )
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        this.reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Valider une réservation par son code de validation (Admin/Agent)"
    )
    @PatchMapping({"/validate/{validationCode}"})
    public ResponseEntity<ReservationResponseDto> validateReservationByCode(@PathVariable String validationCode) {
        Reservation validatedReservation = this.reservationService.validateReservation(validationCode);
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
        } else {
            dto.setClientNom(reservation.getClientNom() + (reservation.getClientPrenom() != null ? " " + reservation.getClientPrenom() : ""));
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
    public AdminReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
