package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Trajet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Make sure to import Query
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {

    List<Trajet> findByVilleDepartContainingIgnoreCaseAndVilleDestinationContainingIgnoreCase(String villeDepart, String villeDestination);

    boolean existsByVilleDepartAndVilleDestinationAndQuartierDepartAndQuartierDestination(String villeDepart, String villeDestination, String quartierDepart, String quartierDestination);

    Optional<Trajet> findByVilleDepartAndVilleDestinationAndQuartierDepartAndQuartierDestination(
        String villeDepart,
        String villeDestination,
        String quartierDepart,
        String quartierDestination
    );

    Optional<Trajet> findByVilleDepartIgnoreCaseAndVilleDestinationIgnoreCase(String villeDepart, String villeDestination);

    // --- NOUVELLES MÉTHODES À AJOUTER POUR RÉCUPÉRER LES VILLES UNIQUES ---
    // Ces requêtes JPQL sélectionnent les valeurs distinctes des colonnes villeDepart et villeArrivee
    // de l'entité Trajet.

    @Query("SELECT DISTINCT t.villeDepart FROM Trajet t")
    List<String> findDistinctVilleDepart();

    @Query("SELECT DISTINCT t.villeArrivee FROM Trajet t")
    List<String> findDistinctVilleArrivee();
}
