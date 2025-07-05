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

    // Votre méthode existante pour la recherche
    List<Trajet> searchTrajets(String villeDepart, String villeDestination);

    // NOUVELLES MÉTHODES POUR RÉCUPÉRER LES VILLES UNIQUES
    List<String> getDistinctVillesDepart();
    List<String> getDistinctVillesArrivee(); // Utilisez "Arrivee" pour la cohérence avec le frontend
}
