package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.exception.DuplicateResourceException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.TrajetRepository; // Assurez-vous que le repository est importé
import PFE.CDSIR_AGENCY.service.TrajetService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrajetServiceImpl implements TrajetService {

    private static final Logger logger = LoggerFactory.getLogger(TrajetServiceImpl.class);
    private final TrajetRepository trajetRepository;

    public TrajetServiceImpl(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trajet> getAllTrajets() {
        logger.info("Récupération de tous les trajets.");
        return trajetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
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
        // existingTrajet.setVoyages(trajetDetails.getVoyages()); // <-- Assurez-vous que cette ligne est gérée correctement si vous la décommentez un jour.

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

    @Override
    @Transactional(readOnly = true)
    public List<Trajet> searchTrajets(String villeDepart, String villeDestination) {
        logger.info("Recherche de trajets de {} à {}.", villeDepart, villeDestination);
        // Vous devez utiliser la méthode de recherche appropriée du repository ici.
        // Par exemple:
        return trajetRepository.findByVilleDepartAndVilleDestination(villeDepart, villeDestination);
        // Ou une recherche plus flexible si nécessaire:
        // return trajetRepository.findByVilleDepartContainingIgnoreCaseAndVilleDestinationContainingIgnoreCase(villeDepart, villeDestination);
    }

    // --- NOUVELLES MÉTHODES À IMPLÉMENTER POUR RÉCUPÉRER LES VILLES UNIQUES ---

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctVillesDepart() {
        logger.info("Récupération des villes de départ uniques.");
        return trajetRepository.findDistinctVilleDepart(); // Appelle la méthode du repository
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctVillesArrivee() {
        logger.info("Récupération des villes d'arrivée uniques.");
        return trajetRepository.findDistinctVilleArrivee(); // Appelle la méthode du repository
    }
}
