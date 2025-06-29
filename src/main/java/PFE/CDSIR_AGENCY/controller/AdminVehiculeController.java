//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.entity.Vehicule;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.VehiculeService;
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
@RequestMapping({"/api/admin/vehicules"})
@PreAuthorize("hasRole('ADMIN')")
public class AdminVehiculeController {
    private final VehiculeService vehiculeService;

    @GetMapping
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        List<Vehicule> vehicules = this.vehiculeService.getAllVehicules();
        return ResponseEntity.ok(vehicules);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        Vehicule vehicule = (Vehicule)this.vehiculeService.getVehiculeById(id).orElseThrow(() -> new NotFoundException("Vehicule non trouvé avec l'ID : " + id));
        return ResponseEntity.ok(vehicule);
    }

    @PostMapping
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        Vehicule savedVehicule = this.vehiculeService.createVehicule(vehicule);
        return new ResponseEntity(savedVehicule, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        Vehicule updatedVehicule = this.vehiculeService.updateVehicule(id, vehicule);
        return ResponseEntity.ok(updatedVehicule);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        this.vehiculeService.deleteVehicule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/agence/{agenceId}"})
    public ResponseEntity<List<Vehicule>> getByAgence(@PathVariable Long agenceId) {
        List<Vehicule> vehicules = this.vehiculeService.getVehiculesByAgence(agenceId);
        return vehicules.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vehicules);
    }

    @GetMapping({"/available"})
    public ResponseEntity<List<Vehicule>> getAvailableVehicules(@RequestParam Long agenceId, @RequestParam int requiredCapacity) {
        List<Vehicule> vehicules = this.vehiculeService.getAvailableVehicules(agenceId, requiredCapacity);
        return vehicules.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vehicules);
    }

    @Generated
    public AdminVehiculeController(final VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }
}
