//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Administrateur;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    Optional<Administrateur> findByNumeroCni(String numeroCni);

    boolean existsByNumeroCni(String numeroCni);
}
