//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.entity.Voyage;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.VoyageService;
import java.time.LocalDate;
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
@RequestMapping({"/api/voyages"})
public class VoyageController {
	private final VoyageService voyageService;

	@GetMapping
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Voyage>> getAllVoyages() {
		List<Voyage> voyages = this.voyageService.getAllVoyages();
		return ResponseEntity.ok(voyages);
	}

	@GetMapping({"/{id}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Voyage> getVoyageById(@PathVariable Long id) {
		Voyage voyage = (Voyage)this.voyageService.getVoyageById(id).orElseThrow(() -> new NotFoundException("Voyage non trouvé avec l'ID : " + id));
		return ResponseEntity.ok(voyage);
	}

	@GetMapping({"/search"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Voyage>> getAvailableVoyages(@RequestParam String villeDepart, @RequestParam String villeDestination, @RequestParam LocalDate dateDepart) {
		List<Voyage> voyages = this.voyageService.getAvailableVoyages(villeDepart, villeDestination, dateDepart);
		return voyages.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(voyages);
	}

	@GetMapping({"/agence/{agenceId}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Voyage>> getByAgence(@PathVariable Long agenceId) {
		List<Voyage> voyages = this.voyageService.getVoyagesByAgence(agenceId);
		return voyages.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(voyages);
	}

	@Generated
	public VoyageController(final VoyageService voyageService) {
		this.voyageService = voyageService;
	}
}
