package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> findByCodeValidation(String validationCode);
	List<Reservation> findByClientId(Long clientId);
	List<Reservation> findByVoyageId(Long voyageId);
	List<Reservation> findByStatut(String statut);
	boolean existsByVoyageIdAndSiegeReserver(Long voyageId, String siegeReserver);
	Optional<Reservation> findByPaymentReference(String paymentReference);

	@Query("SELECT r FROM Reservation r WHERE LOWER(r.statut) = LOWER(:status)")
	List<Reservation> findByStatutIgnoreCase(@Param("status") String status);

	@Query(value = "SELECT * FROM reservation r " +
			"LEFT JOIN client c ON r.client_id = c.id_client " +
			"WHERE (c.id_client IS NOT NULL AND (" +
			"  LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"  LOWER(c.prenom) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
			")) OR " +
			"(r.client_email IS NOT NULL AND LOWER(r.client_email) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
			"LOWER(r.code_validation) LIKE LOWER(CONCAT('%', :keyword, '%'))",
			nativeQuery = true)
	List<Reservation> searchReservations(@Param("keyword") String keyword);
}