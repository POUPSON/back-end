package PFE.CDSIR_AGENCY.controller;

import PFE.CDSIR_AGENCY.dto.HistoriqueDTO;
import PFE.CDSIR_AGENCY.service.HistoriqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
@Tag(name = "Historique API", description = "Gestion des historiques de réservations et colis")
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    public HistoriqueController(HistoriqueService historiqueService) {
        this.historiqueService = historiqueService;
    }

    @GetMapping
    @Operation(summary = "Liste tous les historiques")
    public List<HistoriqueDTO> getAllHistoriques() {
        return historiqueService.getAllHistoriques();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un historique par son ID")
    public HistoriqueDTO getHistoriqueById(@PathVariable Long id) {
        return historiqueService.getHistoriqueById(id);
    }

    @GetMapping("/client/{clientId}/reservations")
    @Operation(summary = "Historique des réservations d'un client")
    public List<HistoriqueDTO> getReservationsByClient(@PathVariable Long clientId) {
        return historiqueService.getHistoriqueReservationsByClient(clientId);
    }

    @GetMapping("/client/{clientId}/colis")
    @Operation(summary = "Historique des colis (envoi/réception) d'un client")
    public List<HistoriqueDTO> getColisByClient(@PathVariable Long clientId) {
        return historiqueService.getHistoriqueColisByClient(clientId);
    }

    @GetMapping("/filter")
    @Operation(summary = "Historique avec filtres optionnels")
    public List<HistoriqueDTO> getHistoriqueWithFilters(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long reservationId,
            @RequestParam(required = false) Long colisId) {
        return historiqueService.getHistoriqueWithFilters(clientId, reservationId, colisId);
    }
}