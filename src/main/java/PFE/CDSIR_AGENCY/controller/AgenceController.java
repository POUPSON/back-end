//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.AgenceService;
import java.util.List;
import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/agences"})
public class AgenceController {
	private final AgenceService agenceService;

	@GetMapping
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Agence>> getAllAgences() {
		List<Agence> agences = this.agenceService.getAllAgences();
		return ResponseEntity.ok(agences);
	}

	@GetMapping({"/{id}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Agence> getAgenceById(@PathVariable Long id) {
		Agence agence = (Agence)this.agenceService.getAgenceById(id).orElseThrow(() -> new NotFoundException("Agence non trouvée avec l'ID : " + id));
		return ResponseEntity.ok(agence);
	}

	@Generated
	public AgenceController(final AgenceService agenceService) {
		this.agenceService = agenceService;
	}
}
