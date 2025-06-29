package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.dto.HistoriqueDTO;
import PFE.CDSIR_AGENCY.entity.Historique;
import PFE.CDSIR_AGENCY.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    public HistoriqueService(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    private HistoriqueDTO convertToDto(Historique historique) {
        HistoriqueDTO dto = new HistoriqueDTO();
        dto.setId(historique.getId());
        dto.setDescription(historique.getDescription());
        dto.setTypeEvenement(historique.getTypeEvenement());
        dto.setDateEvenement(historique.getDateEvenement());
        dto.setDateCreation(historique.getDateCreation());

        if (historique.getReservation() != null) {
            dto.setReservationId(historique.getReservation().getId());
            dto.setReservationStatut(historique.getReservation().getStatut());
        }

        if (historique.getColis() != null) {
            dto.setColisId(historique.getColis().getId());
            dto.setColisTrackingNumber(historique.getColis().getTrackingNumber());
            // Conversion de ColisStatus enum vers String
            dto.setColisStatut(historique.getColis().getStatut().name());
        }

        if (historique.getClient() != null) {
            dto.setClientId(historique.getClient().getId());
            dto.setClientNomComplet(
                    historique.getClient().getPrenom() + " " + historique.getClient().getNom()
            );
        }

        if (historique.getAdministrateur() != null) {
            dto.setAdministrateurId(historique.getAdministrateur().getId());
            dto.setAdministrateurNom(historique.getAdministrateur().getNomAdministrateur());
        }

        return dto;
    }

    // ... (le reste des méthodes reste inchangé)
    public List<HistoriqueDTO> getAllHistoriques() {
        return historiqueRepository.findAllByOrderByDateEvenementDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HistoriqueDTO> getHistoriqueReservationsByClient(Long clientId) {
        return historiqueRepository.findHistoriqueReservationsByClient(clientId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HistoriqueDTO> getHistoriqueColisByClient(Long clientId) {
        return historiqueRepository.findHistoriqueColisByClient(clientId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HistoriqueDTO> getHistoriqueWithFilters(Long clientId, Long reservationId, Long colisId) {
        return historiqueRepository.findWithFilters(clientId, reservationId, colisId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public HistoriqueDTO getHistoriqueById(Long id) {
        return historiqueRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }
}