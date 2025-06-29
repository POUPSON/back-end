//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.TrajetService;
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
@RequestMapping({"/api/trajets"})
public class TrajetController {
	private final TrajetService trajetService;

	@GetMapping
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Trajet>> getAllTrajets() {
		List<Trajet> trajets = this.trajetService.getAllTrajets();
		return ResponseEntity.ok(trajets);
	}

	@GetMapping({"/{id}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Trajet> getTrajetById(@PathVariable Long id) {
		Trajet trajet = (Trajet)this.trajetService.getTrajetById(id).orElseThrow(() -> new NotFoundException("Trajet non trouvé avec l'ID : " + id));
		return ResponseEntity.ok(trajet);
	}

	@GetMapping({"/search"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Trajet>> searchTrajets(@RequestParam String depart, @RequestParam String destination) {
		List<Trajet> trajets = this.trajetService.searchTrajets(depart, destination);
		return trajets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(trajets);
	}

	@Generated
	public TrajetController(final TrajetService trajetService) {
		this.trajetService = trajetService;
	}
}
