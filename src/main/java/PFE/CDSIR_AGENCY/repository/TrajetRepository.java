package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importez l'annotation Query
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    // NOUVELLES MÉTHODES POUR RÉCUPÉRER LES VILLES UNIQUES

    /**
     * Récupère toutes les villes de départ uniques et triées de la table trajet.
     * @return Une liste de chaînes de caractères représentant les villes de départ uniques.
     */
    @Query("SELECT DISTINCT t.villeDepart FROM Trajet t ORDER BY t.villeDepart ASC")
    List<String> findDistinctVilleDepart();

    /**
     * Récupère toutes les villes de destination uniques et triées de la table trajet.
     * @return Une liste de chaînes de caractères représentant les villes de destination uniques.
     */
    @Query("SELECT DISTINCT t.villeDestination FROM Trajet t ORDER BY t.villeDestination ASC")
    List<String> findDistinctVilleArrivee(); // Utilisez "Arrivee" pour la cohérence avec le frontend

}
