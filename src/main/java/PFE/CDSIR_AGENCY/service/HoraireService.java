package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.entity.Horaire;
import java.util.List;
import java.util.Optional;

public interface HoraireService {
	List<Horaire> getAllHoraires();
	Optional<Horaire> getHoraireById(Long id);
	Horaire createHoraire(Horaire horaire);
	Horaire updateHoraire(Long id, Horaire horaire);
	void deleteHoraire(Long id);
}