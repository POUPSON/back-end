//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.entity.Horaire;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.service.HoraireService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/admin/horaires"})
@PreAuthorize("hasRole('ADMIN')")
public class AdminHoraireController {
    private final HoraireService horaireService;

    @GetMapping
    public ResponseEntity<List<Horaire>> getAllHoraires() {
        List<Horaire> horaires = this.horaireService.getAllHoraires();
        return ResponseEntity.ok(horaires);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Horaire> getHoraireById(@PathVariable Long id) {
        Horaire horaire = (Horaire)this.horaireService.getHoraireById(id).orElseThrow(() -> new NotFoundException("Horaire non trouvé avec l'ID : " + id));
        return ResponseEntity.ok(horaire);
    }

    @PostMapping
    public ResponseEntity<Horaire> createHoraire(@RequestBody Horaire horaire) {
        Horaire savedHoraire = this.horaireService.createHoraire(horaire);
        return new ResponseEntity(savedHoraire, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Horaire> updateHoraire(@PathVariable Long id, @RequestBody Horaire horaire) {
        Horaire updatedHoraire = this.horaireService.updateHoraire(id, horaire);
        return ResponseEntity.ok(updatedHoraire);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteHoraire(@PathVariable Long id) {
        this.horaireService.deleteHoraire(id);
        return ResponseEntity.noContent().build();
    }

    @Generated
    public AdminHoraireController(final HoraireService horaireService) {
        this.horaireService = horaireService;
    }
}
