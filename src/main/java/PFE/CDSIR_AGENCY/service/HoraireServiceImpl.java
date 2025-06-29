//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.entity.Horaire;
import PFE.CDSIR_AGENCY.exception.DuplicateResourceException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.HoraireRepository;
import PFE.CDSIR_AGENCY.service.HoraireService;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HoraireServiceImpl implements HoraireService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(HoraireServiceImpl.class);
	private final HoraireRepository horaireRepository;

	@Transactional(
			readOnly = true
	)
	public List<Horaire> getAllHoraires() {
		log.info("Récupération de tous les horaires.");
		return this.horaireRepository.findAll();
	}

	@Transactional(
			readOnly = true
	)
	public Optional<Horaire> getHoraireById(Long id) {
		log.info("Récupération de l'horaire avec l'ID : {}", id);
		return this.horaireRepository.findById(id);
	}

	public Horaire createHoraire(Horaire horaire) {
		log.info("Création d'un nouvel horaire : Départ={}, Arrivée={}", horaire.getHeureDepart(), horaire.getHeureArrive());
		if (this.horaireRepository.existsByHeureDepartAndHeureArrive(horaire.getHeureDepart(), horaire.getHeureArrive())) {
			log.warn("Tentative de création d'horaire avec des heures de départ et d'arrivée existantes : {} -> {}", horaire.getHeureDepart(), horaire.getHeureArrive());
			throw new DuplicateResourceException("Un horaire avec l'heure de départ '" + horaire.getHeureDepart() + "' et l'heure d'arrivée '" + horaire.getHeureArrive() + "' existe déjà.");
		} else {
			if (horaire.getStatut() == null || horaire.getStatut().isEmpty()) {
				horaire.setStatut("ACTIVE");
			}

			return (Horaire)this.horaireRepository.save(horaire);
		}
	}

	public Horaire updateHoraire(Long id, Horaire horaireDetails) {
		log.info("Mise à jour de l'horaire avec l'ID : {}", id);
		Horaire horaire = (Horaire)this.horaireRepository.findById(id).orElseThrow(() -> {
			log.error("Horaire non trouvé pour la mise à jour avec l'ID : {}", id);
			return new NotFoundException("Horaire non trouvé avec l'ID : " + id);
		});
		if ((!horaire.getHeureDepart().equals(horaireDetails.getHeureDepart()) || !horaire.getHeureArrive().equals(horaireDetails.getHeureArrive())) && this.horaireRepository.existsByHeureDepartAndHeureArrive(horaireDetails.getHeureDepart(), horaireDetails.getHeureArrive())) {
			log.warn("Tentative de mise à jour de l'horaire avec des heures de départ et d'arrivée déjà utilisées par un autre horaire: {} -> {}", horaireDetails.getHeureDepart(), horaireDetails.getHeureArrive());
			throw new DuplicateResourceException("Un autre horaire avec l'heure de départ '" + horaireDetails.getHeureDepart() + "' et l'heure d'arrivée '" + horaireDetails.getHeureArrive() + "' existe déjà.");
		} else {
			horaire.setHeureDepart(horaireDetails.getHeureDepart());
			horaire.setHeureArrive(horaireDetails.getHeureArrive());
			horaire.setDuree(horaireDetails.getDuree());
			horaire.setStatut(horaireDetails.getStatut());
			return (Horaire)this.horaireRepository.save(horaire);
		}
	}

	public void deleteHoraire(Long id) {
		log.info("Suppression de l'horaire avec l'ID : {}", id);
		if (!this.horaireRepository.existsById(id)) {
			log.error("Horaire non trouvé pour la suppression avec l'ID : {}", id);
			throw new NotFoundException("Horaire non trouvé avec l'ID : " + id);
		} else {
			this.horaireRepository.deleteById(id);
		}
	}

	@Generated
	public HoraireServiceImpl(final HoraireRepository horaireRepository) {
		this.horaireRepository = horaireRepository;
	}
}
