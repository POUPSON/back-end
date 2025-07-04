//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.entity.Trajet;
import java.util.List;
import java.util.Optional;

public interface TrajetService {
	List<Trajet> getAllTrajets();

	Optional<Trajet> getTrajetById(Long id);

	Trajet createTrajet(Trajet trajet);

	Trajet updateTrajet(Long id, Trajet trajetDetails);

	void deleteTrajet(Long id);
// AJOUTEZ CETTE LIGNE : Déclaration de la méthode searchTrajets
    List<Trajet> searchTrajets(String villeDepart, String villeDestination);

}
    
