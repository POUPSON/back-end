//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Trajet;
import PFE.CDSIR_AGENCY.entity.Voyage;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
	List<Voyage> findByDateDepartAndStatutIgnoreCase(LocalDate dateDepart, String statut);

	List<Voyage> findByTrajetAndDateDepartAndStatutIgnoreCase(Trajet trajet, LocalDate dateDepart, String statut);

	List<Voyage> findByAgenceId(Long agenceId);

	List<Voyage> findByTrajetVilleDepartIgnoreCaseAndTrajetVilleDestinationIgnoreCaseAndDateDepartAndStatutIn(String villeDepart, String villeDestination, LocalDate dateDepart, List<String> statuts);

	List<Voyage> findByDateDepart(LocalDate dateDepart);
}
