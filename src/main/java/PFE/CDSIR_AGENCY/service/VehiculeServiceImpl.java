//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.entity.Vehicule;
import PFE.CDSIR_AGENCY.exception.DuplicateResourceException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.AgenceRepository;
import PFE.CDSIR_AGENCY.repository.VehiculeRepository;
import PFE.CDSIR_AGENCY.service.VehiculeService;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(VehiculeServiceImpl.class);
	private final VehiculeRepository vehiculeRepository;
	private final AgenceRepository agenceRepository;

	@Transactional(
			readOnly = true
	)
	public List<Vehicule> getAllVehicules() {
		log.info("Récupération de tous les véhicules.");
		return this.vehiculeRepository.findAll();
	}

	@Transactional(
			readOnly = true
	)
	public Optional<Vehicule> getVehiculeById(Long id) {
		log.info("Récupération du véhicule avec l'ID : {}", id);
		return this.vehiculeRepository.findById(id);
	}

	public Vehicule createVehicule(Vehicule vehicule) {
		log.info("Création d'un nouveau véhicule : Immatriculation={}", vehicule.getImmatriculation());
		if (vehicule.getAgence() != null && vehicule.getAgence().getId() != null) {
			Agence agence = (Agence)this.agenceRepository.findById(vehicule.getAgence().getId()).orElseThrow(() -> new NotFoundException("Agence non trouvée avec l'ID : " + vehicule.getAgence().getId()));
			vehicule.setAgence(agence);
			if (this.vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
				log.warn("Tentative de création d'un véhicule avec une immatriculation dupliquée : {}", vehicule.getImmatriculation());
				throw new DuplicateResourceException("Un véhicule avec cette immatriculation existe déjà : " + vehicule.getImmatriculation());
			} else {
				if (vehicule.getStatut() == null || vehicule.getStatut().isEmpty()) {
					vehicule.setStatut("DISPONIBLE");
				}

				return (Vehicule)this.vehiculeRepository.save(vehicule);
			}
		} else {
			throw new IllegalArgumentException("L'agence associée au véhicule ne peut pas être nulle.");
		}
	}

	public Vehicule updateVehicule(Long id, Vehicule vehiculeDetails) {
		log.info("Mise à jour du véhicule avec l'ID : {}", id);
		Vehicule vehicule = (Vehicule)this.vehiculeRepository.findById(id).orElseThrow(() -> {
			log.error("Vehicule non trouvé pour la mise à jour avec l'ID : {}", id);
			return new NotFoundException("Vehicule non trouvé avec l'ID : " + id);
		});
		if (vehiculeDetails.getAgence() != null && vehiculeDetails.getAgence().getId() != null && !vehicule.getAgence().getId().equals(vehiculeDetails.getAgence().getId())) {
			Agence newAgence = (Agence)this.agenceRepository.findById(vehiculeDetails.getAgence().getId()).orElseThrow(() -> new NotFoundException("Nouvelle agence non trouvée avec l'ID : " + vehiculeDetails.getAgence().getId()));
			vehicule.setAgence(newAgence);
		}

		if (!vehicule.getImmatriculation().equalsIgnoreCase(vehiculeDetails.getImmatriculation()) && this.vehiculeRepository.existsByImmatriculation(vehiculeDetails.getImmatriculation())) {
			log.warn("Tentative de mise à jour du véhicule avec une immatriculation déjà utilisée: {}", vehiculeDetails.getImmatriculation());
			throw new DuplicateResourceException("Une autre véhicule avec cette immatriculation existe déjà : " + vehiculeDetails.getImmatriculation());
		} else {
			vehicule.setMarque(vehiculeDetails.getMarque());
			vehicule.setModele(vehiculeDetails.getModele());
			vehicule.setAnneeFabrication(vehiculeDetails.getAnneeFabrication());
			vehicule.setCapacite(vehiculeDetails.getCapacite());
			vehicule.setImmatriculation(vehiculeDetails.getImmatriculation());
			vehicule.setStatut(vehiculeDetails.getStatut());
			return (Vehicule)this.vehiculeRepository.save(vehicule);
		}
	}

	public void deleteVehicule(Long id) {
		log.info("Suppression du véhicule avec l'ID : {}", id);
		if (!this.vehiculeRepository.existsById(id)) {
			log.error("Vehicule non trouvé pour la suppression avec l'ID : {}", id);
			throw new NotFoundException("Vehicule non trouvé avec l'ID : " + id);
		} else {
			this.vehiculeRepository.deleteById(id);
		}
	}

	@Transactional(
			readOnly = true
	)
	public List<Vehicule> getVehiculesByAgence(Long agenceId) {
		log.info("Récupération des véhicules pour l'agence avec l'ID : {}", agenceId);
		if (!this.agenceRepository.existsById(agenceId)) {
			throw new NotFoundException("Agence non trouvée avec l'ID : " + agenceId);
		} else {
			return this.vehiculeRepository.findByAgenceId(agenceId);
		}
	}

	@Transactional(
			readOnly = true
	)
	public List<Vehicule> getAvailableVehicules(Long agenceId, int requiredCapacity) {
		log.info("Récupération des véhicules disponibles pour l'agence {} avec une capacité minimale de {}", agenceId, requiredCapacity);
		return this.vehiculeRepository.findByAgenceIdAndStatutIgnoreCaseAndCapaciteGreaterThanEqual(agenceId, "DISPONIBLE", requiredCapacity);
	}

	@Generated
	public VehiculeServiceImpl(final VehiculeRepository vehiculeRepository, final AgenceRepository agenceRepository) {
		this.vehiculeRepository = vehiculeRepository;
		this.agenceRepository = agenceRepository;
	}
}
