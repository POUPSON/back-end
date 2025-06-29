//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.entity.Agence;
import java.util.List;
import java.util.Optional;

public interface AgenceService {
	List<Agence> getAllAgences();

	Optional<Agence> getAgenceById(Long id);

	Agence createAgence(Agence agence);

	Agence updateAgence(Long id, Agence agenceDetails);

	void deleteAgence(Long id);

	Agence updateAgenceStatut(Long id, String statut);
}
