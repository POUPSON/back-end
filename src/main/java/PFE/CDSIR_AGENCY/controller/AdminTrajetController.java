//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.TrajetService;
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
@RequestMapping({"/api/admin/trajets"})
@PreAuthorize("hasRole('ADMIN')")
public class AdminTrajetController {
    private final TrajetService trajetService;

    @GetMapping
    public ResponseEntity<List<Trajet>> getAllTrajets() {
        List<Trajet> trajets = this.trajetService.getAllTrajets();
        return ResponseEntity.ok(trajets);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Trajet> getTrajetById(@PathVariable Long id) {
        Trajet trajet = (Trajet)this.trajetService.getTrajetById(id).orElseThrow(() -> new NotFoundException("Trajet non trouvé avec l'ID : " + id));
        return ResponseEntity.ok(trajet);
    }

    @PostMapping
    public ResponseEntity<Trajet> createTrajet(@RequestBody Trajet trajet) {
        Trajet savedTrajet = this.trajetService.createTrajet(trajet);
        return new ResponseEntity(savedTrajet, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Trajet> updateTrajet(@PathVariable Long id, @RequestBody Trajet trajet) {
        Trajet updatedTrajet = this.trajetService.updateTrajet(id, trajet);
        return ResponseEntity.ok(updatedTrajet);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        this.trajetService.deleteTrajet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/search"})
    public ResponseEntity<List<Trajet>> searchTrajets(@RequestParam String depart, @RequestParam String destination) {
        List<Trajet> trajets = this.trajetService.searchTrajets(depart, destination);
        return trajets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(trajets);
    }

    @Generated
    public AdminTrajetController(final TrajetService trajetService) {
        this.trajetService = trajetService;
    }
}
