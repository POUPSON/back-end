//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.dto.VoyageRequestDto;
import PFE.CDSIR_AGENCY.entity.Voyage;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.VoyageService;
import jakarta.validation.Valid;
import java.time.LocalDate;
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
@RequestMapping({"/api/admin/voyages"})
@PreAuthorize("hasRole('ADMIN')")
public class AdminVoyageController {
    private final VoyageService voyageService;

    @GetMapping
    public ResponseEntity<List<Voyage>> getAllVoyages() {
        List<Voyage> voyages = this.voyageService.getAllVoyages();
        return ResponseEntity.ok(voyages);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Voyage> getVoyageById(@PathVariable Long id) {
        Voyage voyage = (Voyage)this.voyageService.getVoyageById(id).orElseThrow(() -> new NotFoundException("Voyage non trouvé avec l'ID : " + id));
        return ResponseEntity.ok(voyage);
    }

    @PostMapping
    public ResponseEntity<Voyage> createVoyage(@RequestBody @Valid VoyageRequestDto voyageRequestDto) {
        Voyage createdVoyage = this.voyageService.createVoyage(voyageRequestDto);
        return new ResponseEntity(createdVoyage, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Voyage> updateVoyage(@PathVariable Long id, @RequestBody @Valid VoyageRequestDto voyageRequestDto) {
        Voyage updatedVoyage = this.voyageService.updateVoyage(id, voyageRequestDto);
        return ResponseEntity.ok(updatedVoyage);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteVoyage(@PathVariable Long id) {
        this.voyageService.deleteVoyage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/search"})
    public ResponseEntity<List<Voyage>> getAvailableVoyages(@RequestParam String villeDepart, @RequestParam String villeDestination, @RequestParam LocalDate dateDepart) {
        List<Voyage> voyages = this.voyageService.getAvailableVoyages(villeDepart, villeDestination, dateDepart);
        return voyages.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(voyages);
    }

    @GetMapping({"/agence/{agenceId}"})
    public ResponseEntity<List<Voyage>> getByAgence(@PathVariable Long agenceId) {
        List<Voyage> voyages = this.voyageService.getVoyagesByAgence(agenceId);
        return voyages.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(voyages);
    }

    @Generated
    public AdminVoyageController(final VoyageService voyageService) {
        this.voyageService = voyageService;
    }
}
