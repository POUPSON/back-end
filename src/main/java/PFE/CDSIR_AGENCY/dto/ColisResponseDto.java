package PFE.CDSIR_AGENCY.dto;

import PFE.CDSIR_AGENCY.entity.Colis;
import lombok.Generated;
import java.util.Objects; // Importez Objects pour le hashCode et equals

public class ColisResponseDto {
    private Long idColis;
    private String description;
    private Double weight;
    private String dimensions;
    private Double estimatedCost;
    private String trackingNumber;
    private Colis.ColisStatus statut;
    private String dateEnregistrement;
    private String dateExpedition;
    private String dateArriveeAgenceDestination;
    private String dateLivraisonPrevue;
    private String dateLivraisonReelle;
    private String datePaiement;

    private Long clientId;
    private String senderName;
    private String senderPhone;
    private String senderEmail;
    private String villeOrigine;
    private String quartierExpedition;
    private String nomDestinataire;
    private String numeroDestinataire;
    private String emailDestinataire;
    private String villeDeDestination;
    private String quartierDestination;
    private String modePaiement;
    private String paymentReference;
    private String codeValidation;
    private Long agenceId;
    private String agenceNom;
    private Long assignedVoyageId;
    private String assignedVoyageDescription;
    private String assignedVoyageVehiculeImmatriculation;

    @Generated
    public Long getIdColis() {
        return this.idColis;
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
    public Colis.ColisStatus getStatut() {
        return this.statut;
    }

    @Generated
    public String getDateEnregistrement() {
        return this.dateEnregistrement;
    }

    @Generated
    public String getDateExpedition() {
        return this.dateExpedition;
    }

    @Generated
    public String getDateArriveeAgenceDestination() {
        return this.dateArriveeAgenceDestination;
    }

    @Generated
    public String getDateLivraisonPrevue() {
        return this.dateLivraisonPrevue;
    }

    @Generated
    public String getDateLivraisonReelle() {
        return this.dateLivraisonReelle;
    }

    @Generated
    public String getDatePaiement() {
        return this.datePaiement;
    }

    @Generated
    public Long getClientId() {
        return this.clientId;
    }

    @Generated
    public String getSenderName() {
        return this.senderName;
    }

    @Generated
    public String getSenderPhone() {
        return this.senderPhone;
    }

    @Generated
    public String getSenderEmail() {
        return this.senderEmail;
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
    public String getPaymentReference() {
        return this.paymentReference;
    }

    @Generated
    public String getCodeValidation() {
        return this.codeValidation;
    }

    @Generated
    public Long getAgenceId() {
        return this.agenceId;
    }

    @Generated
    public String getAgenceNom() {
        return this.agenceNom;
    }

    @Generated
    public Long getAssignedVoyageId() {
        return this.assignedVoyageId;
    }

    @Generated
    public String getAssignedVoyageDescription() {
        return this.assignedVoyageDescription;
    }

    @Generated
    public String getAssignedVoyageVehiculeImmatriculation() {
        return this.assignedVoyageVehiculeImmatriculation;
    }

    @Generated
    public void setIdColis(final Long idColis) {
        this.idColis = idColis;
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
    public void setStatut(final Colis.ColisStatus statut) {
        this.statut = statut;
    }

    @Generated
    public void setDateEnregistrement(final String dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    @Generated
    public void setDateExpedition(final String dateExpedition) {
        this.dateExpedition = dateExpedition;
    }

    @Generated
    public void setDateArriveeAgenceDestination(final String dateArriveeAgenceDestination) {
        this.dateArriveeAgenceDestination = dateArriveeAgenceDestination;
    }

    @Generated
    public void setDateLivraisonPrevue(final String dateLivraisonPrevue) {
        this.dateLivraisonPrevue = dateLivraisonPrevue;
    }

    @Generated
    public void setDateLivraisonReelle(final String dateLivraisonReelle) {
        this.dateLivraisonReelle = dateLivraisonReelle;
    }

    @Generated
    public void setDatePaiement(final String datePaiement) {
        this.datePaiement = datePaiement;
    }

    @Generated
    public void setClientId(final Long clientId) {
        this.clientId = clientId;
    }

    @Generated
    public void setSenderName(final String senderName) {
        this.senderName = senderName;
    }

    @Generated
    public void setSenderPhone(final String senderPhone) {
        this.senderPhone = senderPhone;
    }

    @Generated
    public void setSenderEmail(final String senderEmail) {
        this.senderEmail = senderEmail;
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
    public void setPaymentReference(final String paymentReference) {
        this.paymentReference = paymentReference;
    }

    @Generated
    public void setCodeValidation(final String codeValidation) {
        this.codeValidation = codeValidation;
    }

    @Generated
    public void setAgenceId(final Long agenceId) {
        this.agenceId = agenceId;
    }

    @Generated
    public void setAgenceNom(final String agenceNom) {
        this.agenceNom = agenceNom;
    }

    @Generated
    public void setAssignedVoyageId(final Long assignedVoyageId) {
        this.assignedVoyageId = assignedVoyageId;
    }

    @Generated
    public void setAssignedVoyageDescription(final String assignedVoyageDescription) {
        this.assignedVoyageDescription = assignedVoyageDescription;
    }

    @Generated
    public void setAssignedVoyageVehiculeImmatriculation(final String assignedVoyageVehiculeImmatriculation) {
        this.assignedVoyageVehiculeImmatriculation = assignedVoyageVehiculeImmatriculation;
    }

    // Réécriture des méthodes equals et hashCode pour éviter l'erreur de "missing return statement"
    @Override
    @Generated
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColisResponseDto that = (ColisResponseDto) o;
        return Objects.equals(idColis, that.idColis) &&
               Objects.equals(description, that.description) &&
               Objects.equals(weight, that.weight) &&
               Objects.equals(dimensions, that.dimensions) &&
               Objects.equals(estimatedCost, that.estimatedCost) &&
               Objects.equals(trackingNumber, that.trackingNumber) &&
               statut == that.statut &&
               Objects.equals(dateEnregistrement, that.dateEnregistrement) &&
               Objects.equals(dateExpedition, that.dateExpedition) &&
               Objects.equals(dateArriveeAgenceDestination, that.dateArriveeAgenceDestination) &&
               Objects.equals(dateLivraisonPrevue, that.dateLivraisonPrevue) &&
               Objects.equals(dateLivraisonReelle, that.dateLivraisonReelle) &&
               Objects.equals(datePaiement, that.datePaiement) &&
               Objects.equals(clientId, that.clientId) &&
               Objects.equals(senderName, that.senderName) &&
               Objects.equals(senderPhone, that.senderPhone) &&
               Objects.equals(senderEmail, that.senderEmail) &&
               Objects.equals(villeOrigine, that.villeOrigine) &&
               Objects.equals(quartierExpedition, that.quartierExpedition) &&
               Objects.equals(nomDestinataire, that.nomDestinataire) &&
               Objects.equals(numeroDestinataire, that.numeroDestinataire) &&
               Objects.equals(emailDestinataire, that.emailDestinataire) &&
               Objects.equals(villeDeDestination, that.villeDeDestination) &&
               Objects.equals(quartierDestination, that.quartierDestination) &&
               Objects.equals(modePaiement, that.modePaiement) &&
               Objects.equals(paymentReference, that.paymentReference) &&
               Objects.equals(codeValidation, that.codeValidation) &&
               Objects.equals(agenceId, that.agenceId) &&
               Objects.equals(agenceNom, that.agenceNom) &&
               Objects.equals(assignedVoyageId, that.assignedVoyageId) &&
               Objects.equals(assignedVoyageDescription, that.assignedVoyageDescription) &&
               Objects.equals(assignedVoyageVehiculeImmatriculation, that.assignedVoyageVehiculeImmatriculation);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(idColis, description, weight, dimensions, estimatedCost, trackingNumber, statut,
                            dateEnregistrement, dateExpedition, dateArriveeAgenceDestination, dateLivraisonPrevue,
                            dateLivraisonReelle, datePaiement, clientId, senderName, senderPhone, senderEmail,
                            villeOrigine, quartierExpedition, nomDestinataire, numeroDestinataire, emailDestinataire,
                            villeDeDestination, quartierDestination, modePaiement, paymentReference, codeValidation,
                            agenceId, agenceNom, assignedVoyageId, assignedVoyageDescription,
                            assignedVoyageVehiculeImmatriculation);
    }

    @Generated
    public String toString() {
        return "ColisResponseDto(idColis=" + this.getIdColis() + ", description=" + this.getDescription() + ", weight=" + this.getWeight() + ", dimensions=" + this.getDimensions() + ", estimatedCost=" + this.getEstimatedCost() + ", trackingNumber=" + this.getTrackingNumber() + ", statut=" + this.getStatut() + ", dateEnregistrement=" + this.getDateEnregistrement() + ", dateExpedition=" + this.getDateExpedition() + ", dateArriveeAgenceDestination=" + this.getDateArriveeAgenceDestination() + ", dateLivraisonPrevue=" + this.getDateLivraisonPrevue() + ", dateLivraisonReelle=" + this.getDateLivraisonReelle() + ", datePaiement=" + this.getDatePaiement() + ", clientId=" + this.getClientId() + ", senderName=" + this.getSenderName() + ", senderPhone=" + this.getSenderPhone() + ", senderEmail=" + this.getSenderEmail() + ", villeOrigine=" + this.getVilleOrigine() + ", quartierExpedition=" + this.getQuartierExpedition() + ", nomDestinataire=" + this.getNomDestinataire() + ", numeroDestinataire=" + this.getNumeroDestinataire() + ", emailDestinataire=" + this.getEmailDestinataire() + ", villeDeDestination=" + this.getVilleDeDestination() + ", quartierDestination=" + this.getQuartierDestination() + ", modePaiement=" + this.getModePaiement() + ", paymentReference=" + this.getPaymentReference() + ", codeValidation=" + this.getCodeValidation() + ", agenceId=" + this.getAgenceId() + ", agenceNom=" + this.getAgenceNom() + ", assignedVoyageId=" + this.getAssignedVoyageId() + ", assignedVoyageDescription=" + this.getAssignedVoyageDescription() + ", assignedVoyageVehiculeImmatriculation=" + this.getAssignedVoyageVehiculeImmatriculation() + ")";
    }
}
