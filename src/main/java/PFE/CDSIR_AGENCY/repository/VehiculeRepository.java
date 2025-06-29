//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Vehicule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
	Optional<Vehicule> findByImmatriculation(String immatriculation);

	boolean existsByImmatriculation(String immatriculation);

	List<Vehicule> findByAgenceId(Long agenceId);

	List<Vehicule> findByAgenceIdAndStatutIgnoreCaseAndCapaciteGreaterThanEqual(Long agenceId, String statut, Integer capacite);
}
