package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.AgenceService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/admin/agences"})

public class AdminAgenceController {
    private final AgenceService agenceService;

    @GetMapping
    public ResponseEntity<List<Agence>> getAllAgences() {
        List<Agence> agences = this.agenceService.getAllAgences();
        return ResponseEntity.ok(agences);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Agence> getAgenceById(@PathVariable Long id) {
        Agence agence = (Agence)this.agenceService.getAgenceById(id).orElseThrow(() -> new NotFoundException("Agence non trouvée avec l'ID : " + id));
        return ResponseEntity.ok(agence);
    }

    @PostMapping
    public ResponseEntity<Agence> createAgence(@RequestBody Agence agence) {
        Agence savedAgence = this.agenceService.createAgence(agence);
        return new ResponseEntity(savedAgence, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Agence> updateAgence(@PathVariable Long id, @RequestBody Agence agence) {
        Agence updatedAgence = this.agenceService.updateAgence(id, agence);
        return ResponseEntity.ok(updatedAgence);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteAgence(@PathVariable Long id) {
        this.agenceService.deleteAgence(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping({"/{id}/statut"})
    public ResponseEntity<Agence> updateAgenceStatut(@PathVariable Long id, @RequestParam String statut) {
        Agence updatedAgence = this.agenceService.updateAgenceStatut(id, statut);
        return ResponseEntity.ok(updatedAgence);
    }

    @Generated
    public AdminAgenceController(final AgenceService agenceService) {
        this.agenceService = agenceService;
    }
}
