//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.dto.ColisRequestDto;
import PFE.CDSIR_AGENCY.dto.ColisResponseDto;
import PFE.CDSIR_AGENCY.service.ColisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/admin/colis"})
@Tag(
        name = "Gestion Colis (Admin)",
        description = "API pour la gestion complète des colis par les administrateurs et agents"
)
public class AdminColisController {
    private final ColisService colisService;

    @Operation(
            summary = "Créer un nouveau colis (Admin/Agent)"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ColisResponseDto> createColis(@RequestBody @Valid ColisRequestDto colisRequestDto) {
        ColisResponseDto createdColis = this.colisService.createColis(colisRequestDto);
        return new ResponseEntity(createdColis, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtenir tous les colis (Admin)"
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ColisResponseDto>> getAllColis() {
        List<ColisResponseDto> colisList = this.colisService.getAllColis();
        return ResponseEntity.ok(colisList);
    }

    @Operation(
            summary = "Obtenir un colis par son ID (Admin/Agent)"
    )
    @GetMapping({"/{id}"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ColisResponseDto> getColisById(@PathVariable Long id) {
        ColisResponseDto colis = this.colisService.getColisById(id);
        return ResponseEntity.ok(colis);
    }

    @Operation(
            summary = "Mettre à jour un colis existant (Admin/Agent)"
    )
    @PutMapping({"/{id}"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ColisResponseDto> updateColis(@PathVariable Long id, @RequestBody @Valid ColisRequestDto colisRequestDto) {
        ColisResponseDto updatedColis = this.colisService.updateColis(id, colisRequestDto);
        return ResponseEntity.ok(updatedColis);
    }

    @Operation(
            summary = "Supprimer un colis par son ID (Admin)"
    )
    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteColis(@PathVariable Long id) {
        this.colisService.deleteColis(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Rechercher un colis par numéro de suivi (Admin/Agent)"
    )
    @GetMapping({"/tracking/{trackingNumber}"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ColisResponseDto> getColisByTrackingNumberForAdmin(@PathVariable String trackingNumber) {
        ColisResponseDto colis = this.colisService.getColisByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(colis);
    }

    @Operation(
            summary = "Assigner un colis à un voyage (Admin/Agent)"
    )
    @PostMapping({"/{colisId}/assign-to-voyage/{voyageId}"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ColisResponseDto> assignColisToVoyage(@PathVariable Long colisId, @PathVariable Long voyageId) {
        ColisResponseDto updatedColis = this.colisService.assignColisToVoyage(colisId, voyageId);
        return ResponseEntity.ok(updatedColis);
    }

    @Operation(
            summary = "Mettre à jour le statut d'un colis (Admin/Agent)"
    )
    @PutMapping({"/{colisId}/status"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<ColisResponseDto> updateColisStatus(@PathVariable Long colisId, @RequestParam String newStatus) {
        ColisResponseDto updatedColis = this.colisService.updateColisStatus(colisId, newStatus);
        return ResponseEntity.ok(updatedColis);
    }

    @Operation(
            summary = "Envoyer la notification de prise en charge à l'expéditeur"
    )
    @PostMapping({"/{colisId}/notify-sender-pickup"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<String> notifySenderPickup(@PathVariable Long colisId) {
        this.colisService.sendSenderPickupNotification(colisId);
        return ResponseEntity.ok("Notification de prise en charge envoyée à l'expéditeur.");
    }

    @Operation(
            summary = "Envoyer la notification colis prêt à être retiré au destinataire"
    )
    @PostMapping({"/{colisId}/notify-recipient-ready"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<String> notifyRecipientReady(@PathVariable Long colisId) {
        this.colisService.sendRecipientReadyForPickupNotification(colisId);
        return ResponseEntity.ok("Notification de colis prêt pour retrait envoyée au destinataire.");
    }

    @Operation(
            summary = "Envoyer la notification de confirmation de livraison"
    )
    @PostMapping({"/{colisId}/notify-delivery-confirmation"})
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<String> notifyDeliveryConfirmation(@PathVariable Long colisId) {
        this.colisService.sendDeliveryConfirmationNotification(colisId);
        return ResponseEntity.ok("Notification de confirmation de livraison envoyée.");
    }

    @Generated
    public AdminColisController(final ColisService colisService) {
        this.colisService = colisService;
    }
}
