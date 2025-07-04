package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.exception.DuplicateResourceException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.TrajetRepository;
import PFE.CDSIR_AGENCY.service.TrajetService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Importez cette annotation

@Service
public class TrajetServiceImpl implements TrajetService {

    private static final Logger logger = LoggerFactory.getLogger(TrajetServiceImpl.class);
    private final TrajetRepository trajetRepository;

    public TrajetServiceImpl(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    @Override
    @Transactional(readOnly = true) // <-- AJOUTEZ CETTE LIGNE
    public List<Trajet> getAllTrajets() {
        logger.info("Récupération de tous les trajets.");
        // Si 'voyages' est une collection lazy-loaded, cette ligne va la charger
        // car la session est encore ouverte grâce à @Transactional
        return trajetRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Trajet> getTrajetById(Long id) {
        logger.info("Récupération du trajet avec l'ID : {}", id);
        return trajetRepository.findById(id);
    }

    @Override
    @Transactional
    public Trajet createTrajet(Trajet trajet) {
        logger.info("Tentative de création d'un nouveau trajet : Départ={}, Destination={}", trajet.getVilleDepart(), trajet.getVilleDestination());

        Optional<Trajet> existingTrajet = trajetRepository.findByVilleDepartAndVilleDestinationAndQuartierDepartAndQuartierDestination(
            trajet.getVilleDepart(),
            trajet.getVilleDestination(),
            trajet.getQuartierDepart(),
            trajet.getQuartierDestination()
        );

        if (existingTrajet.isPresent()) {
            logger.warn("Tentative de création d'un trajet dupliqué : {} -> {}", trajet.getVilleDepart(), trajet.getVilleDestination());
            throw new DuplicateResourceException("Un trajet identique existe déjà de '" + trajet.getVilleDepart() + "' à '" + trajet.getVilleDestination() + "'.");
        }

        Trajet savedTrajet = trajetRepository.save(trajet);
        logger.info("Trajet créé avec succès avec l'ID : {}", savedTrajet.getId());
        return savedTrajet;
    }

    @Override
    @Transactional
    public Trajet updateTrajet(Long id, Trajet trajetDetails) {
        logger.info("Mise à jour du trajet avec l'ID : {}", id);
        Trajet existingTrajet = trajetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trajet non trouvé avec l'ID : " + id));

        existingTrajet.setVilleDepart(trajetDetails.getVilleDepart());
        existingTrajet.setVilleDestination(trajetDetails.getVilleDestination());
        existingTrajet.setQuartierDepart(trajetDetails.getQuartierDepart());
        existingTrajet.setQuartierDestination(trajetDetails.getQuartierDestination());
        existingTrajet.setStatut(trajetDetails.getStatut());
        // existingTrajet.setVoyages(trajetDetails.getVoyages()); // <-- ATTENTION : Si vous avez cette ligne, elle peut causer des problèmes si la collection n'est pas gérée.

        Trajet updatedTrajet = trajetRepository.save(existingTrajet);
        logger.info("Trajet avec l'ID {} mis à jour avec succès.", updatedTrajet.getId());
        return updatedTrajet;
    }

    @Override
    @Transactional
    public void deleteTrajet(Long id) {
        logger.info("Suppression du trajet avec l'ID : {}", id);
        if (!trajetRepository.existsById(id)) {
            throw new NotFoundException("Trajet non trouvé avec l'ID : " + id);
        }
        trajetRepository.deleteById(id);
        logger.info("Trajet avec l'ID {} supprimé avec succès.", id);
    }
}
