package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.dto.LoginRequest;
import PFE.CDSIR_AGENCY.dto.LoginResponse;
import PFE.CDSIR_AGENCY.dto.PasswordResetRequest;
import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Map;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; // Importez RequestMethod
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200", "https://cdsir-agency-frontend.onrender.com"}) // ✅ AJOUTEZ L'URL DE VOTRE FRONTEND DÉPLOYÉ ICI
@RestController
@RequestMapping({"/api/clients"})
@Tag(
		name = "Clients",
		description = "API pour l'authentification et la gestion des clients"
)
public class ClientController {
	private final ClientService clientService;

	@Generated
	public ClientController(final ClientService clientService) {
		this.clientService = clientService;
	}

	// NOUVEAU : Gère explicitement les requêtes OPTIONS pour tous les sous-chemins de /api/clients
    @RequestMapping(method = RequestMethod.OPTIONS, value = "/**")
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

	@Operation(
			summary = "Enregistrer un nouveau client"
	)
	@ApiResponses({@ApiResponse(
			responseCode = "201",
			description = "Client enregistré avec succès",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							implementation = Client.class
					)
			)}
	), @ApiResponse(
			responseCode = "400",
			description = "Données d'entrée invalides",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							example = "{\"message\": \"L'email doit être valide\"}"
					)
			)}
	), @ApiResponse(
			responseCode = "409",
			description = "Email, téléphone ou CNI déjà utilisé",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							example = "{\"message\": \"L'email est déjà utilisé\"}"
					)
			)}
	)})
	@PostMapping({"/register"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Client> registerClient(@RequestBody @Valid Client client) {
		Client savedClient = this.clientService.enregistrerClient(client);
		return new ResponseEntity(savedClient, HttpStatus.CREATED);
	}

	@Operation(
			summary = "Connecter un client"
	)
	@ApiResponses({@ApiResponse(
			responseCode = "200",
			description = "Connexion réussie",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							implementation = LoginResponse.class
					)
			)}
	), @ApiResponse(
			responseCode = "401",
			description = "Email ou mot de passe invalide",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							example = "{\"message\": \"Email ou mot de passe invalide\"}"
					)
			)}
	)})
	// @CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"}) // SUPPRIMÉ : Redondant avec la classe et la config globale
	@PostMapping({"/login"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<LoginResponse> loginClient(@RequestBody @Valid LoginRequest loginRequest) {
		LoginResponse response = this.clientService.loginClient(loginRequest.getEmail(), loginRequest.getMotPasse());
		return ResponseEntity.ok(response);
	}

	@Operation(
			summary = "Initier la réinitialisation du mot de passe"
	)
	@ApiResponses({@ApiResponse(
			responseCode = "200",
			description = "Lien de réinitialisation envoyé si l'email existe"
	), @ApiResponse(
			responseCode = "404",
			description = "Client non trouvé pour l'email fourni (ou message générique pour sécurité)",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							example = "{\"message\": \"Client non trouvé avec l'email: test@example.com\"}"
					)
			)}
	)})
	@PostMapping({"/reset-password/initiate"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Map<String, String>> initiatePasswordReset(@RequestParam @Valid String email) {
		this.clientService.initierReinitialisationMotDePasse(email);
		return ResponseEntity.ok(Collections.singletonMap("message", "Si l'email existe, un lien de réinitialisation a été envoyé."));
	}

	@Operation(
			summary = "Compléter la réinitialisation du mot de passe"
	)
	@ApiResponses({@ApiResponse(
			responseCode = "200",
			description = "Mot de passe mis à jour avec succès"
	), @ApiResponse(
			responseCode = "400",
			description = "Token invalide, expiré ou mot de passe non conforme",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							example = "{\"message\": \"Token de réinitialisation invalide ou expiré\"}"
					)
			)}
	), @ApiResponse(
			responseCode = "404",
			description = "Client non trouvé",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							example = "{\"message\": \"Client non trouvé\"}"
					)
			)}
	)})
	@PostMapping({"/reset-password/complete"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Map<String, String>> completePasswordReset(@RequestBody @Valid PasswordResetRequest request) {
		this.clientService.completerReinitialisationMotDePasse(request);
		return ResponseEntity.ok(Collections.singletonMap("message", "Mot de passe mis à jour avec succès"));
	}

	@Operation(
			summary = "Obtenir le profil du client connecté"
	)
	@ApiResponses({@ApiResponse(
			responseCode = "200",
			description = "Profil du client récupéré",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							implementation = Client.class
					)
			)}
	), @ApiResponse(
			responseCode = "404",
			description = "Client non trouvé",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							example = "{\"message\": \"Client non trouvé avec l'ID : 1\"}"
					)
			)}
	), @ApiResponse(
			responseCode = "403",
			description = "Accès refusé"
	)})
	@GetMapping({"/me"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Client> getMyProfile() {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		Client client = this.clientService.getClientById(currentClientId);
		return ResponseEntity.ok(client);
	}

	@Operation(
			summary = "Mettre à jour le profil du client connecté"
	)
	@ApiResponses({@ApiResponse(
			responseCode = "200",
			description = "Profil du client mis à jour",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema(
							implementation = Client.class
					)
			)}
	), @ApiResponse(
			responseCode = "400",
			description = "Données d'entrée invalides"
	), @ApiResponse(
			responseCode = "404",
			description = "Client non trouvé"
	), @ApiResponse(
			responseCode = "409",
			description = "Email, téléphone ou CNI déjà utilisé"
	), @ApiResponse(
			responseCode = "403",
			description = "Accès refusé"
	)})
	@PutMapping({"/me"})
	@PreAuthorize("hasRole('CLIENT')")
	public ResponseEntity<Client> updateMyProfile(@RequestBody @Valid Client updatedClient) {
		Long currentClientId = this.clientService.getCurrentAuthenticatedClientId();
		Client client = this.clientService.updateClient(currentClientId, updatedClient);
		return ResponseEntity.ok(client);
	}
}
