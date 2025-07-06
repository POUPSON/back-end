// src/main/java/PFE/CDSIR_AGENCY/entity/Voyage.java
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
import lombok.Generated; // Make sure Lombok is set up for this entity too

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

    @Column(name = "description") // ADD THIS FIELD
    private String description; // ADD THIS FIELD

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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
    private @NotNull(
            message = "Le voyage doit être associé à une agence"
    ) Agence agence;

    @OneToMany(
            mappedBy = "voyage",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Reservation> reservations;

    @Generated
    public Voyage() {
    }

    // Add new getter and setter for description
    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public void setDescription(String description) {
        this.description = description;
    }

    // Existing generated getters and setters below...
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
    public List<Reservation> getReservations() { // This was cut off in your input
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
    public void setReservations(List<Reservation> reservations) { // This was cut off in your input
        this.reservations = reservations;
    }


    // Existing equals, hashCode, toString methods will be regenerated by Lombok
    // with the new description field if you recompile or use @Data annotation.
    // If not using @Data, make sure to manually add description to them or rely on Lombok regeneration.
}
