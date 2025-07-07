package PFE.CDSIR_AGENCY.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects; // Importez Objects pour les méthodes equals et hashCode
import lombok.Generated;

@Entity
@Table(
        name = "voyage"
)
public class Voyage {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id_voyage"
    )
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(
            name = "date_depart"
    )
    private @NotNull(
            message = "La date de départ est requise"
    ) @FutureOrPresent(
            message = "La date de départ ne peut pas être dans le passé"
    ) LocalDate dateDepart;
    @Column(
            name = "date_arrivee"
    )
    private @NotNull(
            message = "La date d'arrivée est requise"
    ) @FutureOrPresent(
            message = "La date d'arrivée ne peut pas être dans le passé"
    ) LocalDate dateArrivee;
    @Column(
            name = "prix",
            precision = 10,
            scale = 2
    )
    private @NotNull(
            message = "Le prix est requis"
    ) @DecimalMin(
            value = "0.01",
            message = "Le prix doit être supérieur à 0"
    ) BigDecimal prix;
    @Column(
            name = "statut",
            length = 20
    )
    private @NotBlank(
            message = "Le statut est requis"
    ) @Size(
            max = 20,
            message = "Le statut ne doit pas dépasser 20 caractères"
    ) String statut;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_trajet",
            nullable = false
    )
    // @JsonIgnore // Supprimé : Généralement pas souhaitable sur ManyToOne
    private @NotNull(
            message = "Le voyage doit être associé à un trajet"
    ) Trajet trajet;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_horaire",
            nullable = false
    )
    // @JsonIgnore // Supprimé : Généralement pas souhaitable sur ManyToOne
    private @NotNull(
            message = "Le voyage doit être associé à un horaire"
    ) Horaire horaire;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_vehicule",
            nullable = false
    )
    // @JsonIgnore // Supprimé : Généralement pas souhaitable sur ManyToOne
    private @NotNull(
            message = "Le voyage doit être associé à un véhicule"
    ) Vehicule vehicule;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_agence",
            nullable = false
    )
    // @JsonIgnore // Supprimé : Généralement pas souhaitable sur ManyToOne
    private @NotNull(
            message = "Le voyage doit être associé à une agence"
    ) Agence agence;

    @OneToMany(
            mappedBy = "voyage",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @JsonIgnore // Gardé : Important pour éviter les boucles infinies de sérialisation
    private List<Reservation> reservations;

    @Generated
    public Voyage() {
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public LocalDate getDateDepart() {
        return this.dateDepart;
    }

    @Generated
    public LocalDate getDateArrivee() {
        return this.dateArrivee;
    }

    @Generated
    public BigDecimal getPrix() {
        return this.prix;
    }

    @Generated
    public String getStatut() {
        return this.statut;
    }

    @Generated
    public Trajet getTrajet() {
        return this.trajet;
    }

    @Generated
    public Horaire getHoraire() {
        return this.horaire;
    }

    @Generated
    public Vehicule getVehicule() {
        return this.vehicule;
    }

    @Generated
    public Agence getAgence() {
        return this.agence;
    }

    @Generated
    public List<Reservation> getReservations() {
        return this.reservations;
    }

    @Generated
    public void setId(Long id) {
        this.id = id;
    }

    @Generated
    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    @Generated
    public void setDateArrivee(LocalDate dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    @Generated
    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    @Generated
    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Generated
    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    @Generated
    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }

    @Generated
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @Generated
    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    @Generated
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    // Réécriture des méthodes equals, hashCode et toString
    @Override
    @Generated
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voyage voyage = (Voyage) o;
        return Objects.equals(id, voyage.id) &&
               Objects.equals(description, voyage.description) &&
               Objects.equals(dateDepart, voyage.dateDepart) &&
               Objects.equals(dateArrivee, voyage.dateArrivee) &&
               Objects.equals(prix, voyage.prix) &&
               Objects.equals(statut, voyage.statut) &&
               Objects.equals(trajet, voyage.trajet) &&
               Objects.equals(horaire, voyage.horaire) &&
               Objects.equals(vehicule, voyage.vehicule) &&
               Objects.equals(agence, voyage.agence);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(id, description, dateDepart, dateArrivee, prix, statut,
                            trajet, horaire, vehicule, agence);
    }

    @Override
    @Generated
    public String toString() {
        return "Voyage{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", dateDepart=" + dateDepart +
               ", dateArrivee=" + dateArrivee +
               ", prix=" + prix +
               ", statut='" + statut + '\'' +
               ", trajet=" + (trajet != null ? trajet.getId() : "null") + // Pour éviter la récursion
               ", horaire=" + (horaire != null ? horaire.getId() : "null") +
               ", vehicule=" + (vehicule != null ? vehicule.getId() : "null") +
               ", agence=" + (agence != null ? agence.getId() : "null") +
               '}';
    }
}
