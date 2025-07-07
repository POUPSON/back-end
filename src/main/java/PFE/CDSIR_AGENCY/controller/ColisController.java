package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.dto.ColisRequestDto;
import PFE.CDSIR_AGENCY.dto.ColisResponseDto;
import PFE.CDSIR_AGENCY.service.ClientService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/colis"})
@Tag(
		name = "Gestion Colis (Client & Public)",
		description = "API pour la gestion des envois de colis par les clients et le suivi public"
)
public class ColisController {
	private final ColisService colisService;
	// ClientService est toujours injecté car d'autres méthodes dans ce contrôleur l'utilisent.
	private final ClientService clientService;

	@Operation(
			summary = "Enregistrer un nouveau colis (accessible à tous, sans authentification requise)"
	)
	@PostMapping
	@PreAuthorize("permitAll()") // MODIFIÉ : Permet l'accès sans authentification
	public ResponseEntity<ColisResponseDto> createColis(@RequestBody @Valid ColisRequestDto colisRequestDto) {
		// Appel de la méthode du service qui ne nécessite pas d'ID client authentifié.
		// Cette méthode sera utilisée pour les dépôts de colis "publics".
		ColisResponseDto createdColis = this.colisService.createColis(colisRequestDto);
		return new ResponseEntity<>(createdColis, HttpStatus.CREATED);
	}

	// NOTE IMPORTANTE : La méthode ci-dessus a été renommée de 'createColisForAuthenticatedClient' à 'createColis'
	// pour mieux refléter qu'elle ne nécessite plus d'authentification spécifique.
	// Si vous avez un formulaire côté client qui envoie un 'clientId', assurez-vous que votre backend
	// peut gérer un colis sans 'clientExpediteur' lié ou que vous avez une logique pour le gérer.
	// La méthode 'createColisForClient' de votre service est toujours disponible pour les cas où un client est authentifié.


	@Operation(
			summary = "Obtenir un colis par son ID (pour le client connecté)"
	)
	@GetMapping({"/{id}"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<ColisResponseDto> getColisByIdForClient(@PathVariable Long id) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		ColisResponseDto colis = this.colisService.getColisByIdAndClientId(id, currentClientId);
		return ResponseEntity.ok(colis);
	}

	@Operation(
			summary = "Mettre à jour un colis du client connecté"
	)
	@PutMapping({"/{id}"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<ColisResponseDto> updateColisForClient(@PathVariable Long id, @RequestBody @Valid ColisRequestDto colisRequestDto) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		ColisResponseDto updatedColis = this.colisService.updateColisForClient(id, currentClientId, colisRequestDto);
		return ResponseEntity.ok(updatedColis);
	}

	@Operation(
			summary = "Annuler un colis du client connecté"
	)
	@DeleteMapping({"/{id}"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Void> cancelColisForClient(@PathVariable Long id) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		this.colisService.cancelColisForClient(id, currentClientId);
		return ResponseEntity.noContent().build();
	}

	@Operation(
			summary = "Obtenir l'historique des colis envoyés par le client connecté"
	)
	@GetMapping({"/my-sent-history"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<List<ColisResponseDto>> getMySentColisHistory() {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		List<ColisResponseDto> colisList = this.colisService.getColisBySenderId(currentClientId);
		return ResponseEntity.ok(colisList);
	}

	@Operation(
			summary = "Obtenir l'historique des colis reçus par le client connecté"
	)
	@GetMapping({"/my-received-history"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<List<ColisResponseDto>> getMyReceivedColisHistory() {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		List<ColisResponseDto> colisList = this.colisService.getColisByRecipientId(currentClientId);
		return ResponseEntity.ok(colisList);
	}

	@Operation(
			summary = "Suivre un colis par son numéro de suivi (accessible à tous)"
	)
	@GetMapping({"/tracking/{trackingNumber}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<ColisResponseDto> trackColisByTrackingNumber(@PathVariable String trackingNumber) {
		ColisResponseDto colis = this.colisService.getColisByTrackingNumber(trackingNumber);
		return ResponseEntity.ok(colis);
	}

	@Generated
	public ColisController(final ColisService colisService, final ClientService clientService) {
		this.colisService = colisService;
		this.clientService = clientService;
	}
}
