package PFE.CDSIR_AGENCY.controller.admin;

import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.exception.DuplicateResourceException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.exception.GlobalExceptionHandler;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200")
public class AdminTrajetController {
    private final TrajetService trajetService;

    @GetMapping
    public ResponseEntity<List<Trajet>> getAllTrajets() {
        List<Trajet> trajets = this.trajetService.getAllTrajets();
        return ResponseEntity.ok(trajets);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Trajet> getTrajetById(@PathVariable Long id) {
        try {
            Trajet trajet = (Trajet)this.trajetService.getTrajetById(id).orElseThrow(() -> new NotFoundException("Trajet non trouvé avec l'ID : " + id));
            return ResponseEntity.ok(trajet);
        } catch (NotFoundException e) {
            GlobalExceptionHandler.ErrorDetails errorDetails = new GlobalExceptionHandler.ErrorDetails(
                java.time.LocalDateTime.now(),
                e.getMessage(),
                "/api/admin/trajets/" + id, // <-- CORRECTION ICI: concaténation de l'ID en String
                HttpStatus.NOT_FOUND.value()
            );
            return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Trajet> createTrajet(@Valid @RequestBody Trajet trajet) {
        try {
            Trajet savedTrajet = this.trajetService.createTrajet(trajet);
            return new ResponseEntity(savedTrajet, HttpStatus.CREATED);
        } catch (DuplicateResourceException e) {
            GlobalExceptionHandler.ErrorDetails errorDetails = new GlobalExceptionHandler.ErrorDetails(
                java.time.LocalDateTime.now(),
                e.getMessage(),
                "/api/admin/trajets", // <-- CORRECTION ICI: URI fixe pour la création
                HttpStatus.CONFLICT.value()
            );
            return new ResponseEntity(errorDetails, HttpStatus.CONFLICT);
        }
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Trajet> updateTrajet(@PathVariable Long id, @Valid @RequestBody Trajet trajet) {
        try {
            Trajet updatedTrajet = this.trajetService.updateTrajet(id, trajet);
            return ResponseEntity.ok(updatedTrajet);
        } catch (NotFoundException e) {
            GlobalExceptionHandler.ErrorDetails errorDetails = new GlobalExceptionHandler.ErrorDetails(
                java.time.LocalDateTime.now(),
                e.getMessage(),
                "/api/admin/trajets/" + id, // <-- CORRECTION ICI: concaténation de l'ID en String
                HttpStatus.NOT_FOUND.value()
            );
            return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
        } catch (DuplicateResourceException e) {
            GlobalExceptionHandler.ErrorDetails errorDetails = new GlobalExceptionHandler.ErrorDetails(
                java.time.LocalDateTime.now(),
                e.getMessage(),
                "/api/admin/trajets/" + id, // <-- CORRECTION ICI: concaténation de l'ID en String
                HttpStatus.CONFLICT.value()
            );
            return new ResponseEntity(errorDetails, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        try {
            this.trajetService.deleteTrajet(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            GlobalExceptionHandler.ErrorDetails errorDetails = new GlobalExceptionHandler.ErrorDetails(
                java.time.LocalDateTime.now(),
                e.getMessage(),
                "/api/admin/trajets/" + id, // <-- CORRECTION ICI: concaténation de l'ID en String
                HttpStatus.NOT_FOUND.value()
            );
            return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping({"/search"})
    public ResponseEntity<List<Trajet>> searchTrajets(@RequestParam String depart, @RequestParam String destination) {
        List<Trajet> trajets = this.trajetService.searchTrajets(depart, destination);
        return trajets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(trajets);
    }

    @GetMapping("/villes-depart")
    public ResponseEntity<List<String>> getDistinctVillesDepart() {
        List<String> villes = trajetService.getDistinctVillesDepart();
        return ResponseEntity.ok(villes);
    }

    @GetMapping("/villes-arrivee")
    public ResponseEntity<List<String>> getDistinctVillesArrivee() {
        List<String> villes = trajetService.getDistinctVillesArrivee();
        return ResponseEntity.ok(villes);
    }

    @Generated
    public AdminTrajetController(final TrajetService trajetService) {
        this.trajetService = trajetService;
    }
}
