
package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.entity.Client;
import PFE.CDSIR_AGENCY.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping({"/api/admin/clients"})
@Tag(
        name = "Administration Clients",
        description = "API pour la gestion des clients par les administrateurs"
)
@PreAuthorize("hasRole('ADMIN')")
public class AdminClientController {
    private final ClientService clientService;

    @Operation(
            summary = "Obtenir tous les clients"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "Liste des clients récupérée avec succès",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Client.class
                    )
            )}
    ), @ApiResponse(
            responseCode = "403",
            description = "Accès refusé"
    )})
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = this.clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @Operation(
            summary = "Obtenir un client par ID"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "Client récupéré avec succès",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Client.class
                    )
            )}
    ), @ApiResponse(
            responseCode = "404",
            description = "Client non trouvé"
    ), @ApiResponse(
            responseCode = "403",
            description = "Accès refusé"
    )})
    @GetMapping({"/{id}"})
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = this.clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @Operation(
            summary = "Créer un nouveau client (par l'administrateur)"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "Client créé avec succès",
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
            responseCode = "409",
            description = "Email, téléphone ou CNI déjà utilisé"
    ), @ApiResponse(
            responseCode = "403",
            description = "Accès refusé"
    )})
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody @Valid Client client) {
        Client savedClient = this.clientService.createClient(client);
        return new ResponseEntity(savedClient, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Mettre à jour un client existant"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "Client mis à jour avec succès",
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
    @PutMapping({"/{id}"})
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody @Valid Client client) {
        Client updatedClient = this.clientService.updateClient(id, client);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(
            summary = "Supprimer un client"
    )
    @ApiResponses({@ApiResponse(
            responseCode = "204",
            description = "Client supprimé avec succès"
    ), @ApiResponse(
            responseCode = "404",
            description = "Client non trouvé"
    ), @ApiResponse(
            responseCode = "403",
            description = "Accès refusé"
    )})
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @Generated
    public AdminClientController(final ClientService clientService) {
        this.clientService = clientService;
    }
}
