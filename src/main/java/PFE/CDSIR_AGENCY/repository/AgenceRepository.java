//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Agence;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
	Optional<Agence> findByNomAgence(String nomAgence);

	boolean existsByNomAgence(String nomAgence);

	boolean existsByTelephoneAgence(String telephoneAgence);
}
