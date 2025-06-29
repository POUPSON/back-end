package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.repository.*;
import PFE.CDSIR_AGENCY.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class JournalisationService {

    private final HistoriqueRepository historiqueRepository;

    public JournalisationService(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Transactional
    public void logReservationCreation(Reservation reservation) {
        Historique historique = new Historique();
        historique.setDescription("Réservation créée - Voyage: " + reservation.getVoyage().getId());
        historique.setReservation(reservation);
        historique.setClient(reservation.getClient());
        historique.setDateEvenement(LocalDateTime.now());
        historiqueRepository.save(historique);
    }

    @Transactional
    public void logColisEnregistrement(Colis colis, Administrateur administrateur) {
        Historique historique = new Historique();
        historique.setDescription("Colis enregistré - Tracking: " + colis.getTrackingNumber());
        historique.setColis(colis);
        historique.setClient(colis.getClientExpediteur());
        historique.setAdministrateur(administrateur);
        historique.setDateEvenement(LocalDateTime.now());
        historiqueRepository.save(historique);
    }

    @Transactional
    public void logColisStatutChange(Colis colis, String ancienStatut, Administrateur administrateur) {
        Historique historique = new Historique();
        historique.setDescription("Changement statut colis: " + ancienStatut + " → " + colis.getStatut());
        historique.setColis(colis);
        historique.setAdministrateur(administrateur);
        historique.setDateEvenement(LocalDateTime.now());
        historiqueRepository.save(historique);
    }

    @Transactional
    public void logSystemEvent(String description, Administrateur administrateur) {
        Historique historique = new Historique();
        historique.setDescription(description);
        historique.setAdministrateur(administrateur);
        historique.setDateEvenement(LocalDateTime.now());
        historiqueRepository.save(historique);
    }
}