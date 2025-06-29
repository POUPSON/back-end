//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service.impl;

import PFE.CDSIR_AGENCY.dto.VoyageRequestDto;
import PFE.CDSIR_AGENCY.entity.Agence;
import PFE.CDSIR_AGENCY.entity.Horaire;
import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.entity.Vehicule;
import PFE.CDSIR_AGENCY.entity.Voyage;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.repository.AgenceRepository;
import PFE.CDSIR_AGENCY.repository.HoraireRepository;
import PFE.CDSIR_AGENCY.repository.TrajetRepository;
import PFE.CDSIR_AGENCY.repository.VehiculeRepository;
import PFE.CDSIR_AGENCY.repository.VoyageRepository;
import PFE.CDSIR_AGENCY.service.VoyageService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VoyageServiceImpl implements VoyageService {
	@Generated
	private static final Logger log = LoggerFactory.getLogger(VoyageServiceImpl.class);
	private final VoyageRepository voyageRepository;
	private final TrajetRepository trajetRepository;
	private final HoraireRepository horaireRepository;
	private final VehiculeRepository vehiculeRepository;
	private final AgenceRepository agenceRepository;

	@Transactional(
			readOnly = true
	)
	public List<Voyage> getAllVoyages() {
		log.info("Récupération de tous les voyages.");
		return this.voyageRepository.findAll();
	}

	@Transactional(
			readOnly = true
	)
	public Optional<Voyage> getVoyageById(Long id) {
		log.info("Récupération du voyage avec l'ID : {}", id);
		return this.voyageRepository.findById(id);
	}

	public Voyage createVoyage(VoyageRequestDto voyageRequestDto) {
		log.info("Création d'un nouveau voyage.");
		Trajet trajet = (Trajet)this.trajetRepository.findById(voyageRequestDto.getTrajetId()).orElseThrow(() -> new NotFoundException("Trajet non trouvé avec l'ID : " + voyageRequestDto.getTrajetId()));
		Horaire horaire = (Horaire)this.horaireRepository.findById(voyageRequestDto.getHoraireId()).orElseThrow(() -> new NotFoundException("Horaire non trouvé avec l'ID : " + voyageRequestDto.getHoraireId()));
		Vehicule vehicule = (Vehicule)this.vehiculeRepository.findById(voyageRequestDto.getVehiculeId()).orElseThrow(() -> new NotFoundException("Véhicule non trouvé avec l'ID : " + voyageRequestDto.getVehiculeId()));
		Agence agence = (Agence)this.agenceRepository.findById(voyageRequestDto.getAgenceId()).orElseThrow(() -> new NotFoundException("Agence non trouvée avec l'ID : " + voyageRequestDto.getAgenceId()));
		Voyage voyage = new Voyage();
		voyage.setTrajet(trajet);
		voyage.setHoraire(horaire);
		voyage.setVehicule(vehicule);
		voyage.setAgence(agence);
		voyage.setDateDepart(voyageRequestDto.getDateDepart());
		voyage.setDateArrivee(voyageRequestDto.getDateArrivee());
		voyage.setPrix(voyageRequestDto.getPrix());
		voyage.setStatut(voyageRequestDto.getStatut() != null ? voyageRequestDto.getStatut() : "PLANIFIE");
		return (Voyage)this.voyageRepository.save(voyage);
	}

	public Voyage updateVoyage(Long id, VoyageRequestDto voyageRequestDto) {
		log.info("Mise à jour du voyage avec l'ID : {}", id);
		Voyage voyage = (Voyage)this.voyageRepository.findById(id).orElseThrow(() -> {
			log.error("Voyage non trouvé pour la mise à jour avec l'ID : {}", id);
			return new NotFoundException("Voyage non trouvé avec l'ID : " + id);
		});
		if (voyageRequestDto.getTrajetId() != null && !voyage.getTrajet().getId().equals(voyageRequestDto.getTrajetId())) {
			Trajet trajet = (Trajet)this.trajetRepository.findById(voyageRequestDto.getTrajetId()).orElseThrow(() -> new NotFoundException("Nouveau Trajet non trouvé avec l'ID : " + voyageRequestDto.getTrajetId()));
			voyage.setTrajet(trajet);
		}

		if (voyageRequestDto.getHoraireId() != null && !voyage.getHoraire().getId().equals(voyageRequestDto.getHoraireId())) {
			Horaire horaire = (Horaire)this.horaireRepository.findById(voyageRequestDto.getHoraireId()).orElseThrow(() -> new NotFoundException("Nouvel Horaire non trouvé avec l'ID : " + voyageRequestDto.getHoraireId()));
			voyage.setHoraire(horaire);
		}

		if (voyageRequestDto.getVehiculeId() != null && !voyage.getVehicule().getId().equals(voyageRequestDto.getVehiculeId())) {
			Vehicule vehicule = (Vehicule)this.vehiculeRepository.findById(voyageRequestDto.getVehiculeId()).orElseThrow(() -> new NotFoundException("Nouveau Véhicule non trouvé avec l'ID : " + voyageRequestDto.getVehiculeId()));
			voyage.setVehicule(vehicule);
		}

		if (voyageRequestDto.getAgenceId() != null && !voyage.getAgence().getId().equals(voyageRequestDto.getAgenceId())) {
			Agence agence = (Agence)this.agenceRepository.findById(voyageRequestDto.getAgenceId()).orElseThrow(() -> new NotFoundException("Nouvelle Agence non trouvée avec l'ID : " + voyageRequestDto.getAgenceId()));
			voyage.setAgence(agence);
		}

		if (voyageRequestDto.getDateDepart() != null) {
			voyage.setDateDepart(voyageRequestDto.getDateDepart());
		}

		if (voyageRequestDto.getDateArrivee() != null) {
			voyage.setDateArrivee(voyageRequestDto.getDateArrivee());
		}

		if (voyageRequestDto.getPrix() != null) {
			voyage.setPrix(voyageRequestDto.getPrix());
		}

		if (voyageRequestDto.getStatut() != null) {
			voyage.setStatut(voyageRequestDto.getStatut());
		}

		return (Voyage)this.voyageRepository.save(voyage);
	}

	public void deleteVoyage(Long id) {
		log.info("Suppression du voyage avec l'ID : {}", id);
		if (!this.voyageRepository.existsById(id)) {
			log.error("Voyage non trouvé pour la suppression avec l'ID : {}", id);
			throw new NotFoundException("Voyage non trouvé avec l'ID : " + id);
		} else {
			this.voyageRepository.deleteById(id);
		}
	}

	@Transactional(
			readOnly = true
	)
	public List<Voyage> getAvailableVoyages(String villeDepart, String villeDestination, LocalDate dateDepart) {
		log.info("Recherche de voyages disponibles de {} à {} à partir du {}", new Object[]{villeDepart, villeDestination, dateDepart});
		List<String> statutsDisponibles = Arrays.asList("PLANIFIE", "DISPONIBLE");
		return this.voyageRepository.findByTrajetVilleDepartIgnoreCaseAndTrajetVilleDestinationIgnoreCaseAndDateDepartAndStatutIn(villeDepart, villeDestination, dateDepart, statutsDisponibles);
	}

	@Transactional(
			readOnly = true
	)
	public List<Voyage> getVoyagesByAgence(Long agenceId) {
		log.info("Récupération des voyages pour l'agence avec l'ID : {}", agenceId);
		if (!this.agenceRepository.existsById(agenceId)) {
			throw new NotFoundException("Agence non trouvée avec l'ID : " + agenceId);
		} else {
			return this.voyageRepository.findByAgenceId(agenceId);
		}
	}

	@Generated
	public VoyageServiceImpl(final VoyageRepository voyageRepository, final TrajetRepository trajetRepository, final HoraireRepository horaireRepository, final VehiculeRepository vehiculeRepository, final AgenceRepository agenceRepository) {
		this.voyageRepository = voyageRepository;
		this.trajetRepository = trajetRepository;
		this.horaireRepository = horaireRepository;
		this.vehiculeRepository = vehiculeRepository;
		this.agenceRepository = agenceRepository;
	}
}
