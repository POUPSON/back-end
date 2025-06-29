package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Colis;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {
	Optional<Colis> findByTrackingNumber(String trackingNumber);

	List<Colis> findByStatut(Colis.ColisStatus statut);

	List<Colis> findByAssignedVoyageId(Long voyageId);

	List<Colis> findByAgenceId(Long agenceId);

	List<Colis> findByClientExpediteurId(Long clientExpediteurId);

	List<Colis> findByEmailExpediteur(String emailExpediteur);

	List<Colis> findByTelephoneExpediteur(String telephoneExpediteur);

	List<Colis> findByNumeroDestinataireOrEmailDestinataire(String numeroDestinataire, String emailDestinataire);

	List<Colis> findByReferencePaiement(String referencePaiement);

	// J'ai ajouté cette méthode basée sur l'erreur précédente
	List<Colis> findByTrackingNumberContainingIgnoreCaseOrNomDestinataireContainingIgnoreCaseOrNomExpediteurContainingIgnoreCase(
			String trackingNumber,
			String nomDestinataire,
			String nomExpediteur
	);

	@Query("SELECT c FROM Colis c WHERE " +
			"LOWER(c.trackingNumber) LIKE LOWER(concat('%', :keyword, '%')) OR " +
			"LOWER(c.nomDestinataire) LIKE LOWER(concat('%', :keyword, '%')) OR " +
			"LOWER(c.nomExpediteur) LIKE LOWER(concat('%', :keyword, '%'))")
	List<Colis> searchByTrackingNumberOrNames(@Param("keyword") String keyword); // C'est une méthode existante, juste pour le contexte.

}