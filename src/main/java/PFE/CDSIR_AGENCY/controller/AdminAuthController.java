
package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.dto.LoginRequest;
import PFE.CDSIR_AGENCY.dto.LoginResponse;
import PFE.CDSIR_AGENCY.entity.Administrateur;
import PFE.CDSIR_AGENCY.service.AdministrateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/admin"})
@Tag(
        name = "Authentification Administrateur",
        description = "API pour l'authentification des administrateurs"
)
public class AdminAuthController {
    private final AdministrateurService administrateurService;

    @Operation(
            summary = "Connecter un administrateur"
    )
    @PostMapping({"/login"})
    public ResponseEntity<LoginResponse> loginAdministrateur(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = this.administrateurService.loginAdministrateur(loginRequest.getEmail(), loginRequest.getMotPasse());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Enregistrer un nouvel administrateur (Accès restreint aux ADMINs existants)"
    )
    @PostMapping({"/register"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Administrateur> registerAdministrateur(@RequestBody @Valid Administrateur administrateur) {
        Administrateur savedAdmin = this.administrateurService.enregistrerAdministrateur(administrateur);
        return new ResponseEntity(savedAdmin, HttpStatus.CREATED);
    }

    @Generated
    public AdminAuthController(final AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }
}
