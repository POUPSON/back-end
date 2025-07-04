package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Trajet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {
	List<Trajet> findByVilleDepartContainingIgnoreCaseAndVilleDestinationContainingIgnoreCase(String villeDepart, String villeDestination);

	boolean existsByVilleDepartAndVilleDestinationAndQuartierDepartAndQuartierDestination(String villeDepart, String villeDestination, String quartierDepart, String quartierDestination);

	  // AJOUTEZ CETTE LIGNE : Déclaration de la méthode pour la vérification des doublons
    Optional<Trajet> findByVilleDepartAndVilleDestinationAndQuartierDepartAndQuartierDestination(
        String villeDepart,
        String villeDestination,
        String quartierDepart,
        String quartierDestination
    );
	Optional<Trajet> findByVilleDepartIgnoreCaseAndVilleDestinationIgnoreCase(String villeDepart, String villeDestination);
}
