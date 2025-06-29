package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Horaire;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraireRepository extends JpaRepository<Horaire, Long> {
    Optional<Horaire> findByHeureDepart(LocalTime heureDepart);

    boolean existsByHeureDepartAndHeureArrive(LocalTime heureDepart, LocalTime heureArrive);
}
