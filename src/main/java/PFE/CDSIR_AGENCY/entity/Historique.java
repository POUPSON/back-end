package PFE.CDSIR_AGENCY.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique")
@Data
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_administrateur")
    private Administrateur administrateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colis")
    private Colis colis;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "date_evenement", nullable = false)
    private LocalDateTime dateEvenement;

    public String getTypeEvenement() {
        if (reservation != null) return "RESERVATION";
        if (colis != null) return "COLIS";
        return "SYSTEME";
    }
}