//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.entity.Vehicule;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.VehiculeService;
import java.util.List;
import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/vehicules"})
public class VehiculeController {
	private final VehiculeService vehiculeService;

	@GetMapping
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Vehicule>> getAllVehicules() {
		List<Vehicule> vehicules = this.vehiculeService.getAllVehicules();
		return ResponseEntity.ok(vehicules);
	}

	@GetMapping({"/{id}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
		Vehicule vehicule = (Vehicule)this.vehiculeService.getVehiculeById(id).orElseThrow(() -> new NotFoundException("Vehicule non trouvé avec l'ID : " + id));
		return ResponseEntity.ok(vehicule);
	}

	@GetMapping({"/agence/{agenceId}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Vehicule>> getByAgence(@PathVariable Long agenceId) {
		List<Vehicule> vehicules = this.vehiculeService.getVehiculesByAgence(agenceId);
		return vehicules.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vehicules);
	}

	@GetMapping({"/available"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Vehicule>> getAvailableVehicules(@RequestParam Long agenceId, @RequestParam int requiredCapacity) {
		List<Vehicule> vehicules = this.vehiculeService.getAvailableVehicules(agenceId, requiredCapacity);
		return vehicules.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vehicules);
	}

	@Generated
	public VehiculeController(final VehiculeService vehiculeService) {
		this.vehiculeService = vehiculeService;
	}
}
