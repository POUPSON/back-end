package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

	// Recherche par identifiants uniques
	Optional<Client> findByEmail(String email);
	Optional<Client> findByTelephone(String telephone);
	Optional<Client> findByNumeroCni(String numeroCni);
	Optional<Client> findByResetToken(String token);

	// Vérification d'existence
	boolean existsByEmail(String email);
	boolean existsByTelephone(String telephone);
	boolean existsByNumeroCni(String numeroCni);

	// Ajout possible pour des requêtes spécifiques
	// Optional<Client> findByEmailAndEnabledTrue(String email);
	// List<Client> findAllByRoleOrderByNomAsc(Client.Role role);
}