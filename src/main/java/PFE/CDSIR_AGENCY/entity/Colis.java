package PFE.CDSIR_AGENCY.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects; // Importez Objects pour le hashCode et equals
import lombok.Generated;

@Entity
@Table(
        name = "colis"
)
public class Colis {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id_colis"
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_agence",
            nullable = false
    )
    private @NotNull(
            message = "L'agence est requise"
    ) Agence agence;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_client_expediteur"
    )
    private Client clientExpediteur;
    @Column(
            name = "description",
            nullable = false
    )
    private @NotBlank(
            message = "La description du colis est requise"
    ) String description;
    @Column(
            name = "weight",
            nullable = false
    )
    private @NotNull(
            message = "Le poids du colis est requis"
    ) @Positive(
            message = "Le poids doit être un nombre positif"
    ) Double weight;
    @Column(
            name = "dimensions"
    )
    private String dimensions;
    @Column(
            name = "estimated_cost",
            nullable = false
    )
    private @NotNull(
            message = "Le coût estimé est requis"
    ) @Positive(
            message = "Le coût estimé doit être un nombre positif"
    ) Double estimatedCost;
    @Column(
            name = "tracking_number",
            unique = true
    )
    private String trackingNumber;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "statut",
            nullable = false
    )
    private ColisStatus statut;
    @Column(
            name = "date_enregistrement",
            nullable = false,
            updatable = false
    )
    private LocalDateTime dateEnregistrement;
    @Column(
            name = "date_expedition"
    )
    private LocalDateTime dateExpedition;
    @Column(
            name = "date_arrivee_agence_destination"
    )
    private LocalDateTime dateArriveeAgenceDestination;
    @Column(
            name = "date_livraison_prevue"
    )
    private LocalDateTime dateLivraisonPrevue;
    @Column(
            name = "date_livraison_reelle"
    )
    private LocalDateTime dateLivraisonReelle;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(
            name = "nom_expediteur",
            nullable = false
    )
    private String nomExpediteur;
    @Column(
            name = "telephone_expediteur",
            nullable = false
    )
    private String telephoneExpediteur;
    @Column(
            name = "email_expediteur"
    )
    private String emailExpediteur;
    @Column(
            name = "ville_origine",
            nullable = false
    )
    private String villeOrigine;
    @Column(
            name = "quartier_expedition"
    )
    private String quartierExpedition;
    @Column(
            name = "nom_destinataire",
            nullable = false
    )
    private String nomDestinataire;
    @Column(
            name = "numero_destinataire",
            nullable = false
    )
    private String numeroDestinataire;
    @Column(
            name = "email_destinataire"
    )
    private String emailDestinataire;
    @Column(
            name = "ville_de_destination",
            nullable = false
    )
    private String villeDeDestination;
    @Column(
            name = "quartier_destination"
    )
    private String quartierDestination;
    @Column(
            name = "mode_paiement",
            nullable = false
    )
    private String modePaiement;
    @Column(
            name = "reference_paiement",
            unique = true
    )
    private String referencePaiement;
    @Column(
            name = "code_validation"
    )
    private String codeValidation;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_voyage_assigne"
    )
    private Voyage assignedVoyage;

    // --- Getters et Setters pour datePaiement ---
    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }
    // ---------------------------------------------

    @Generated
    public Colis() {
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public Agence getAgence() {
        return this.agence;
    }

    @Generated
    public Client getClientExpediteur() {
        return this.clientExpediteur;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public Double getWeight() {
        return this.weight;
    }

    @Generated
    public String getDimensions() {
        return this.dimensions;
    }

    @Generated
    public Double getEstimatedCost() {
        return this.estimatedCost;
    }

    @Generated
    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    @Generated
    public ColisStatus getStatut() {
        return this.statut;
    }

    @Generated
    public LocalDateTime getDateEnregistrement() {
        return this.dateEnregistrement;
    }

    @Generated
    public LocalDateTime getDateExpedition() {
        return this.dateExpedition;
    }

    @Generated
    public LocalDateTime getDateArriveeAgenceDestination() {
        return this.dateArriveeAgenceDestination;
    }

    @Generated
    public LocalDateTime getDateLivraisonPrevue() {
        return this.dateLivraisonPrevue;
    }

    @Generated
    public LocalDateTime getDateLivraisonReelle() {
        return this.dateLivraisonReelle;
    }

    @Generated
    public String getNomExpediteur() {
        return this.nomExpediteur;
    }

    @Generated
    public String getTelephoneExpediteur() {
        return this.telephoneExpediteur;
    }

    @Generated
    public String getEmailExpediteur() {
        return this.emailExpediteur;
    }

    @Generated
    public String getVilleOrigine() {
        return this.villeOrigine;
    }

    @Generated
    public String getQuartierExpedition() {
        return this.quartierExpedition;
    }

    @Generated
    public String getNomDestinataire() {
        return this.nomDestinataire;
    }

    @Generated
    public String getNumeroDestinataire() {
        return this.numeroDestinataire;
    }

    @Generated
    public String getEmailDestinataire() {
        return this.emailDestinataire;
    }

    @Generated
    public String getVilleDeDestination() {
        return this.villeDeDestination;
    }

    @Generated
    public String getQuartierDestination() {
        return this.quartierDestination;
    }

    @Generated
    public String getModePaiement() {
        return this.modePaiement;
    }

    @Generated
    public String getReferencePaiement() {
        return this.referencePaiement;
    }

    @Generated
    public String getCodeValidation() {
        return this.codeValidation;
    }

    @Generated
    public Voyage getAssignedVoyage() {
        return this.assignedVoyage;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setAgence(final Agence agence) {
        this.agence = agence;
    }

    @Generated
    public void setClientExpediteur(final Client clientExpediteur) {
        this.clientExpediteur = clientExpediteur;
    }

    @Generated
    public void setDescription(final String description) {
        this.description = description;
    }

    @Generated
    public void setWeight(final Double weight) {
        this.weight = weight;
    }

    @Generated
    public void setDimensions(final String dimensions) {
        this.dimensions = dimensions;
    }

    @Generated
    public void setEstimatedCost(final Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    @Generated
    public void setTrackingNumber(final String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Generated
    public void setStatut(final ColisStatus statut) {
        this.statut = statut;
    }

    @Generated
    public void setDateEnregistrement(final LocalDateTime dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    @Generated
    public void setDateExpedition(final LocalDateTime dateExpedition) {
        this.dateExpedition = dateExpedition;
    }

    @Generated
    public void setDateArriveeAgenceDestination(final LocalDateTime dateArriveeAgenceDestination) {
        this.dateArriveeAgenceDestination = dateArriveeAgenceDestination;
    }

    @Generated
    public void setDateLivraisonPrevue(final LocalDateTime dateLivraisonPrevue) {
        this.dateLivraisonPrevue = dateLivraisonPrevue;
    }

    @Generated
    public void setDateLivraisonReelle(final LocalDateTime dateLivraisonReelle) {
        this.dateLivraisonReelle = dateLivraisonReelle;
    }

    @Generated
    public void setNomExpediteur(final String nomExpediteur) {
        this.nomExpediteur = nomExpediteur;
    }

    @Generated
    public void setTelephoneExpediteur(final String telephoneExpediteur) {
        this.telephoneExpediteur = telephoneExpediteur;
    }

    @Generated
    public void setEmailExpediteur(final String emailExpediteur) {
        this.emailExpediteur = emailExpediteur;
    }

    @Generated
    public void setVilleOrigine(final String villeOrigine) {
        this.villeOrigine = villeOrigine;
    }

    @Generated
    public void setQuartierExpedition(final String quartierExpedition) {
        this.quartierExpedition = quartierExpedition;
    }

    @Generated
    public void setNomDestinataire(final String nomDestinataire) {
        this.nomDestinataire = nomDestinataire;
    }

    @Generated
    public void setNumeroDestinataire(final String numeroDestinataire) {
        this.numeroDestinataire = numeroDestinataire;
    }

    @Generated
    public void setEmailDestinataire(final String emailDestinataire) {
        this.emailDestinataire = emailDestinataire;
    }

    @Generated
    public void setVilleDeDestination(final String villeDeDestination) {
        this.villeDeDestination = villeDeDestination;
    }

    @Generated
    public void setQuartierDestination(final String quartierDestination) {
        this.quartierDestination = quartierDestination;
    }

    @Generated
    public void setModePaiement(final String modePaiement) {
        this.modePaiement = modePaiement;
    }

    @Generated
    public void setReferencePaiement(final String referencePaiement) {
        this.referencePaiement = referencePaiement;
    }

    @Generated
    public void setCodeValidation(final String codeValidation) {
        this.codeValidation = codeValidation;
    }

    @Generated
    public void setAssignedVoyage(final Voyage assignedVoyage) {
        this.assignedVoyage = assignedVoyage;
    }

    // Ajout des méthodes equals et hashCode générées par Objects
    @Override
    @Generated
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colis colis = (Colis) o;
        return Objects.equals(id, colis.id) &&
               Objects.equals(agence, colis.agence) &&
               Objects.equals(clientExpediteur, colis.clientExpediteur) &&
               Objects.equals(description, colis.description) &&
               Objects.equals(weight, colis.weight) &&
               Objects.equals(dimensions, colis.dimensions) &&
               Objects.equals(estimatedCost, colis.estimatedCost) &&
               Objects.equals(trackingNumber, colis.trackingNumber) &&
               statut == colis.statut &&
               Objects.equals(dateEnregistrement, colis.dateEnregistrement) &&
               Objects.equals(dateExpedition, colis.dateExpedition) &&
               Objects.equals(dateArriveeAgenceDestination, colis.dateArriveeAgenceDestination) &&
               Objects.equals(dateLivraisonPrevue, colis.dateLivraisonPrevue) &&
               Objects.equals(dateLivraisonReelle, colis.dateLivraisonReelle) &&
               Objects.equals(datePaiement, colis.datePaiement) &&
               Objects.equals(nomExpediteur, colis.nomExpediteur) &&
               Objects.equals(telephoneExpediteur, colis.telephoneExpediteur) &&
               Objects.equals(emailExpediteur, colis.emailExpediteur) &&
               Objects.equals(villeOrigine, colis.villeOrigine) &&
               Objects.equals(quartierExpedition, colis.quartierExpedition) &&
               Objects.equals(nomDestinataire, colis.nomDestinataire) &&
               Objects.equals(numeroDestinataire, colis.numeroDestinataire) &&
               Objects.equals(emailDestinataire, colis.emailDestinataire) &&
               Objects.equals(villeDeDestination, colis.villeDeDestination) &&
               Objects.equals(quartierDestination, colis.quartierDestination) &&
               Objects.equals(modePaiement, colis.modePaiement) &&
               Objects.equals(referencePaiement, colis.referencePaiement) &&
               Objects.equals(codeValidation, colis.codeValidation) &&
               Objects.equals(assignedVoyage, colis.assignedVoyage);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(id, agence, clientExpediteur, description, weight, dimensions, estimatedCost,
                            trackingNumber, statut, dateEnregistrement, dateExpedition,
                            dateArriveeAgenceDestination, dateLivraisonPrevue, dateLivraisonReelle,
                            datePaiement, nomExpediteur, telephoneExpediteur, emailExpediteur,
                            villeOrigine, quartierExpedition, nomDestinataire, numeroDestinataire,
                            emailDestinataire, villeDeDestination, quartierDestination,
                            modePaiement, referencePaiement, codeValidation, assignedVoyage);
    }


    public static enum ColisStatus {
        ENREGISTRE,
        EXPEDIE, // AJOUTÉ : L'état EXPEDIE
        EN_TRANSIT,
        ARRIVE_AGENCE_DESTINATION,
        PRET_POUR_RAMASSAGE,
        LIVRE,
        ANNULE,
        PAYE;
    }
}
