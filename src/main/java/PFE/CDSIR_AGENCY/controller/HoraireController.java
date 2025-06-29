//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.entity.Horaire;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.HoraireService;
import java.util.List;
import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/horaires"})
public class HoraireController {
	private final HoraireService horaireService;

	@GetMapping
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Horaire>> getAllHoraires() {
		List<Horaire> horaires = this.horaireService.getAllHoraires();
		return ResponseEntity.ok(horaires);
	}

	@GetMapping({"/{id}"})
	@PreAuthorize("permitAll()")
	public ResponseEntity<Horaire> getHoraireById(@PathVariable Long id) {
		Horaire horaire = (Horaire)this.horaireService.getHoraireById(id).orElseThrow(() -> new NotFoundException("Horaire non trouvé avec l'ID : " + id));
		return ResponseEntity.ok(horaire);
	}

	@Generated
	public HoraireController(final HoraireService horaireService) {
		this.horaireService = horaireService;
	}
}
