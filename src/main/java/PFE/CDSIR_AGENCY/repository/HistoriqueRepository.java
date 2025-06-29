package PFE.CDSIR_AGENCY.repository;

import PFE.CDSIR_AGENCY.entity.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

    // Méthode existante pour les réservations par client
    @Query("SELECT h FROM Historique h WHERE h.client.id = :clientId AND h.reservation IS NOT NULL ORDER BY h.dateEvenement DESC")
    List<Historique> findHistoriqueReservationsByClient(@Param("clientId") Long clientId);

    // Méthode existante pour les colis par client
    @Query("SELECT h FROM Historique h WHERE (h.client.id = :clientId OR h.colis.clientExpediteur.id = :clientId) AND h.colis IS NOT NULL ORDER BY h.dateEvenement DESC")
    List<Historique> findHistoriqueColisByClient(@Param("clientId") Long clientId);

    // Méthode existante avec filtres
    @Query("SELECT h FROM Historique h WHERE " +
            "(:clientId IS NULL OR h.client.id = :clientId) AND " +
            "(:reservationId IS NULL OR h.reservation.id = :reservationId) AND " +
            "(:colisId IS NULL OR h.colis.id = :colisId) " +
            "ORDER BY h.dateEvenement DESC")
    List<Historique> findWithFilters(
            @Param("clientId") Long clientId,
            @Param("reservationId") Long reservationId,
            @Param("colisId") Long colisId);

    // AJOUTS RECOMMANDÉS :

    // 1. Méthode pour récupérer tous les historiques triés
    List<Historique> findAllByOrderByDateEvenementDesc();

    // 2. Méthode pour trouver par type d'événement
    @Query("SELECT h FROM Historique h WHERE h.reservation IS NOT NULL AND :type = 'RESERVATION' OR h.colis IS NOT NULL AND :type = 'COLIS' ORDER BY h.dateEvenement DESC")
    List<Historique> findByTypeEvenement(@Param("type") String type);

    // 3. Méthode pour trouver les historiques récents
    List<Historique> findTop10ByOrderByDateEvenementDesc();

    // 4. Méthode pour trouver par période
    @Query("SELECT h FROM Historique h WHERE h.dateEvenement BETWEEN :start AND :end ORDER BY h.dateEvenement DESC")
    List<Historique> findByPeriod(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}