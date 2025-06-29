//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.exception.DuplicateResourceException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.AgenceRepository;
import PFE.CDSIR_AGENCY.service.AgenceService;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AgenceServiceImpl implements AgenceService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(AgenceServiceImpl.class);
	private final AgenceRepository agenceRepository;

	@Transactional(
			readOnly = true
	)
	public List<Agence> getAllAgences() {
		log.info("Récupération de toutes les agences.");
		return this.agenceRepository.findAll();
	}

	@Transactional(
			readOnly = true
	)
	public Optional<Agence> getAgenceById(Long id) {
		log.info("Récupération de l'agence avec l'ID : {}", id);
		return this.agenceRepository.findById(id);
	}

	public Agence createAgence(Agence agence) {
		log.info("Création d'une nouvelle agence : {}", agence.getNomAgence());
		if (this.agenceRepository.existsByNomAgence(agence.getNomAgence())) {
			log.warn("Tentative de création d'agence avec un nom existant : {}", agence.getNomAgence());
			throw new DuplicateResourceException("Une agence avec le nom '" + agence.getNomAgence() + "' existe déjà.");
		} else if (this.agenceRepository.existsByTelephoneAgence(agence.getTelephoneAgence())) {
			log.warn("Tentative de création d'agence avec un téléphone existant : {}", agence.getTelephoneAgence());
			throw new DuplicateResourceException("Une agence avec le numéro de téléphone '" + agence.getTelephoneAgence() + "' existe déjà.");
		} else {
			if (agence.getStatut() == null || agence.getStatut().isEmpty()) {
				agence.setStatut("ACTIVE");
			}

			return (Agence)this.agenceRepository.save(agence);
		}
	}

	public Agence updateAgence(Long id, Agence agenceDetails) {
		log.info("Mise à jour de l'agence avec l'ID : {}", id);
		Agence agence = (Agence)this.agenceRepository.findById(id).orElseThrow(() -> {
			log.error("Agence non trouvée pour la mise à jour avec l'ID : {}", id);
			return new NotFoundException("Agence non trouvée avec l'ID : " + id);
		});
		if (!agence.getNomAgence().equals(agenceDetails.getNomAgence()) && this.agenceRepository.existsByNomAgence(agenceDetails.getNomAgence())) {
			log.warn("Tentative de mise à jour de l'agence avec un nom déjà utilisé : {}", agenceDetails.getNomAgence());
			throw new DuplicateResourceException("Une autre agence avec le nom '" + agenceDetails.getNomAgence() + "' existe déjà.");
		} else if (!agence.getTelephoneAgence().equals(agenceDetails.getTelephoneAgence()) && this.agenceRepository.existsByTelephoneAgence(agenceDetails.getTelephoneAgence())) {
			log.warn("Tentative de mise à jour de l'agence avec un téléphone déjà utilisé : {}", agenceDetails.getTelephoneAgence());
			throw new DuplicateResourceException("Une autre agence avec le numéro de téléphone '" + agenceDetails.getTelephoneAgence() + "' existe déjà.");
		} else {
			agence.setNomAgence(agenceDetails.getNomAgence());
			agence.setVilleAgence(agenceDetails.getVilleAgence());
			agence.setLocalisation(agenceDetails.getLocalisation());
			agence.setTelephoneAgence(agenceDetails.getTelephoneAgence());
			agence.setStatut(agenceDetails.getStatut());
			return (Agence)this.agenceRepository.save(agence);
		}
	}

	public void deleteAgence(Long id) {
		log.info("Suppression de l'agence avec l'ID : {}", id);
		if (!this.agenceRepository.existsById(id)) {
			log.error("Agence non trouvée pour la suppression avec l'ID : {}", id);
			throw new NotFoundException("Agence non trouvée avec l'ID : " + id);
		} else {
			this.agenceRepository.deleteById(id);
		}
	}

	public Agence updateAgenceStatut(Long id, String statut) {
		log.info("Mise à jour du statut de l'agence {} à {}", id, statut);
		Agence agence = (Agence)this.agenceRepository.findById(id).orElseThrow(() -> {
			log.error("Agence non trouvée pour la mise à jour du statut avec l'ID : {}", id);
			return new NotFoundException("Agence non trouvée avec l'ID : " + id);
		});
		agence.setStatut(statut);
		return (Agence)this.agenceRepository.save(agence);
	}

	@Generated
	public AgenceServiceImpl(final AgenceRepository agenceRepository) {
		this.agenceRepository = agenceRepository;
	}
}
