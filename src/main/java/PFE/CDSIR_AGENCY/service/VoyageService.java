//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.dto.VoyageRequestDto;
import PFE.CDSIR_AGENCY.entity.Voyage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoyageService {
	List<Voyage> getAllVoyages();

	Optional<Voyage> getVoyageById(Long id);

	Voyage createVoyage(VoyageRequestDto voyageRequestDto);

	Voyage updateVoyage(Long id, VoyageRequestDto voyageRequestDto);

	void deleteVoyage(Long id);

	List<Voyage> getAvailableVoyages(String villeDepart, String villeDestination, LocalDate dateDepart);

	List<Voyage> getVoyagesByAgence(Long agenceId);
}
