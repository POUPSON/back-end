//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.exception.DuplicateResourceException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.TrajetRepository;
import PFE.CDSIR_AGENCY.service.TrajetService;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrajetServiceImpl implements TrajetService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(TrajetServiceImpl.class);
	private final TrajetRepository trajetRepository;

	@Transactional(
			readOnly = true
	)
	public List<Trajet> getAllTrajets() {
		log.info("Récupération de tous les trajets.");
		return this.trajetRepository.findAll();
	}

	@Transactional(
			readOnly = true
	)
	public Optional<Trajet> getTrajetById(Long id) {
		log.info("Récupération du trajet avec l'ID : {}", id);
		return this.trajetRepository.findById(id);
	}

	public Trajet createTrajet(Trajet trajet) {
		log.info("Création d'un nouveau trajet : Départ={}, Destination={}", trajet.getVilleDepart(), trajet.getVilleDestination());
		if (this.trajetRepository.existsByVilleDepartAndVilleDestinationAndQuartierDepartAndQuartierDestination(trajet.getVilleDepart(), trajet.getVilleDestination(), trajet.getQuartierDepart(), trajet.getQuartierDestination())) {
			log.warn("Tentative de création d'un trajet dupliqué : {} -> {}", trajet.getVilleDepart(), trajet.getVilleDestination());
			throw new DuplicateResourceException("Un trajet identique existe déjà de '" + trajet.getVilleDepart() + "' à '" + trajet.getVilleDestination() + "'.");
		} else {
			if (trajet.getStatut() == null || trajet.getStatut().isEmpty()) {
				trajet.setStatut("ACTIVE");
			}

			return (Trajet)this.trajetRepository.save(trajet);
		}
	}

	public Trajet updateTrajet(Long id, Trajet trajetDetails) {
		log.info("Mise à jour du trajet avec l'ID : {}", id);
		Trajet trajet = (Trajet)this.trajetRepository.findById(id).orElseThrow(() -> {
			log.error("Trajet non trouvé pour la mise à jour avec l'ID : {}", id);
			return new NotFoundException("Trajet non trouvé avec l'ID : " + id);
		});
		if ((!trajet.getVilleDepart().equalsIgnoreCase(trajetDetails.getVilleDepart()) || !trajet.getVilleDestination().equalsIgnoreCase(trajetDetails.getVilleDestination()) || !trajet.getQuartierDepart().equalsIgnoreCase(trajetDetails.getQuartierDepart()) || !trajet.getQuartierDestination().equalsIgnoreCase(trajetDetails.getQuartierDestination())) && this.trajetRepository.existsByVilleDepartAndVilleDestinationAndQuartierDepartAndQuartierDestination(trajetDetails.getVilleDepart(), trajetDetails.getVilleDestination(), trajetDetails.getQuartierDepart(), trajetDetails.getQuartierDestination())) {
			log.warn("Tentative de mise à jour du trajet avec des attributs déjà utilisés par un autre trajet: {} -> {}", trajetDetails.getVilleDepart(), trajetDetails.getVilleDestination());
			throw new DuplicateResourceException("Un autre trajet avec ces détails existe déjà.");
		} else {
			trajet.setVilleDepart(trajetDetails.getVilleDepart());
			trajet.setVilleDestination(trajetDetails.getVilleDestination());
			trajet.setQuartierDepart(trajetDetails.getQuartierDepart());
			trajet.setQuartierDestination(trajetDetails.getQuartierDestination());
			trajet.setStatut(trajetDetails.getStatut());
			return (Trajet)this.trajetRepository.save(trajet);
		}
	}

	public void deleteTrajet(Long id) {
		log.info("Suppression du trajet avec l'ID : {}", id);
		if (!this.trajetRepository.existsById(id)) {
			log.error("Trajet non trouvé pour la suppression avec l'ID : {}", id);
			throw new NotFoundException("Trajet non trouvé avec l'ID : " + id);
		} else {
			this.trajetRepository.deleteById(id);
		}
	}

	@Transactional(
			readOnly = true
	)
	public List<Trajet> searchTrajets(String depart, String destination) {
		log.info("Recherche de trajets de {} vers {}", depart, destination);
		return this.trajetRepository.findByVilleDepartContainingIgnoreCaseAndVilleDestinationContainingIgnoreCase(depart, destination);
	}

	@Generated
	public TrajetServiceImpl(final TrajetRepository trajetRepository) {
		this.trajetRepository = trajetRepository;
	}
}
