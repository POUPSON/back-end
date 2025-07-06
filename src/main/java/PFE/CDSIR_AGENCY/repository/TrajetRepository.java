package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Trajet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // N'oubliez pas cet import !
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

    @Query("SELECT DISTINCT t.villeDepart FROM Trajet t")
    List<String> findDistinctVilleDepart();

    // --- CORRECTION CRUCIALE ICI : Utilisez 'villeDestination' au lieu de 'villeArrivee' ---
    @Query("SELECT DISTINCT t.villeDestination FROM Trajet t") // <--- C'est la ligne à corriger
    List<String> findDistinctVilleArrivee(); // Le nom de la méthode peut rester "findDistinctVilleArrivee"
}
