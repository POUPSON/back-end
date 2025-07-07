package PFE.CDSIR_AGENCY.dto;

import PFE.CDSIR_AGENCY.entity.Colis;
import lombok.Generated; // Gardez cette annotation si vous utilisez Lombok

public class ColisResponseDto {
    private Long idColis;
    private String description;
    private Double weight;
    private String dimensions;
    private Double estimatedCost;
    private String trackingNumber;
    private Colis.ColisStatus statut;
    // Changement de LocalDateTime à String pour les dates
    private String dateEnregistrement;
    private String dateExpedition;
    private String dateArriveeAgenceDestination;
    private String dateLivraisonPrevue;
    private String dateLivraisonReelle;
    private String datePaiement; // AJOUTÉ : Champ pour la date de paiement

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
    // Changement de LocalDateTime à String
    public String getDateEnregistrement() {
        return this.dateEnregistrement;
    }

    @Generated
    // Changement de LocalDateTime à String
    public String getDateExpedition() {
        return this.dateExpedition;
    }

    @Generated
    // Changement de LocalDateTime à String
    public String getDateArriveeAgenceDestination() {
        return this.dateArriveeAgenceDestination;
    }

    @Generated
    // Changement de LocalDateTime à String
    public String getDateLivraisonPrevue() {
        return this.dateLivraisonPrevue;
    }

    @Generated
    // Changement de LocalDateTime à String
    public String getDateLivraisonReelle() {
        return this.dateLivraisonReelle;
    }

    @Generated
    // AJOUTÉ : Getter pour datePaiement
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
    // Changement de LocalDateTime à String
    public void setDateEnregistrement(final String dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    @Generated
    // Changement de LocalDateTime à String
    public void setDateExpedition(final String dateExpedition) {
        this.dateExpedition = dateExpedition;
    }

    @Generated
    // Changement de LocalDateTime à String
    public void setDateArriveeAgenceDestination(final String dateArriveeAgenceDestination) {
        this.dateArriveeAgenceDestination = dateArriveeAgenceDestination;
    }

    @Generated
    // Changement de LocalDateTime à String
    public void setDateLivraisonPrevue(final String dateLivraisonPrevue) {
        this.dateLivraisonPrevue = dateLivraisonPrevue;
    }

    @Generated
    // Changement de LocalDateTime à String
    public void setDateLivraisonReelle(final String dateLivraisonReelle) {
        this.dateLivraisonReelle = dateLivraisonReelle;
    }

    @Generated
    // AJOUTÉ : Setter pour datePaiement
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

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ColisResponseDto)) {
            return false;
        } else {
            ColisResponseDto other = (ColisResponseDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$idColis = this.getIdColis();
                Object other$idColis = other.getIdColis();
                if (this$idColis == null) {
                    if (other$idColis != null) {
                        return false;
                    }
                } else if (!this$idColis.equals(other$idColis)) {
                    return false;
                }

                Object this$weight = this.getWeight();
                Object other$weight = other.getWeight();
                if (this$weight == null) {
                    if (other$weight != null) {
                        return false;
                    }
                } else if (!this$weight.equals(other$weight)) {
                    return false;
                }

                Object this$estimatedCost = this.getEstimatedCost();
                Object other$estimatedCost = other.getEstimatedCost();
                if (this$estimatedCost == null) {
                    if (other$estimatedCost != null) {
                        return false;
                    }
                } else if (!this$estimatedCost.equals(other$estimatedCost)) {
                    return false;
                }

                Object this$clientId = this.getClientId();
                Object other$clientId = other.getClientId();
                if (this$clientId == null) {
                    if (other$clientId != null) {
                        return false;
                    }
                } else if (!this$clientId.equals(other$clientId)) {
                    return false;
                }

                Object this$agenceId = this.getAgenceId();
                Object other$agenceId = other.getAgenceId();
                if (this$agenceId == null) {
                    if (other$agenceId != null) {
                        return false;
                    }
                } else if (!this$agenceId.equals(other$agenceId)) {
                    return false;
                }

                Object this$assignedVoyageId = this.getAssignedVoyageId();
                Object other$assignedVoyageId = other.getAssignedVoyageId();
                if (this$assignedVoyageId == null) {
                    if (other$assignedVoyageId != null) {
                        return false;
                    }
                } else if (!this$assignedVoyageId.equals(other$assignedVoyageId)) {
                    return false;
                }

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                Object this$dimensions = this.getDimensions();
                Object other$dimensions = other.getDimensions();
                if (this$dimensions == null) {
                    if (other$dimensions != null) {
                        return false;
                    }
                } else if (!this$dimensions.equals(other$dimensions)) {
                    return false;
                }

                Object this$trackingNumber = this.getTrackingNumber();
                Object other$trackingNumber = other.getTrackingNumber();
                if (this$trackingNumber == null) {
                    if (other$trackingNumber != null) {
                        return false;
                    }
                } else if (!this$trackingNumber.equals(other$trackingNumber)) {
                    return false;
                }

                Object this$statut = this.getStatut();
                Object other$statut = other.getStatut();
                if (this$statut == null) {
                    if (other$statut != null) {
                        return false;
                    }
                } else if (!this$statut.equals(other$statut)) {
                    return false;
                }

                Object this$dateEnregistrement = this.getDateEnregistrement();
                Object other$dateEnregistrement = other.getDateEnregistrement();
                if (this$dateEnregistrement == null) {
                    if (other$dateEnregistrement != null) {
                        return false;
                    }
                } else if (!this$dateEnregistrement.equals(other$dateEnregistrement)) {
                    return false;
                }

                Object this$dateExpedition = this.getDateExpedition();
                Object other$dateExpedition = other.getDateExpedition();
                if (this$dateExpedition == null) {
                    if (other$dateExpedition != null) {
                        return false;
                    }
                } else if (!this$dateExpedition.equals(other$dateExpedition)) {
                    return false;
                }

                Object this$dateArriveeAgenceDestination = this.getDateArriveeAgenceDestination();
                Object other$dateArriveeAgenceDestination = other.getDateArriveeAgenceDestination();
                if (this$dateArriveeAgenceDestination == null) {
                    if (other$dateArriveeAgenceDestination != null) {
                        return false;
                    }
                } else if (!this$dateArriveeAgenceDestination.equals(other$dateArriveeAgenceDestination)) {
                    return false;
                }

                Object this$dateLivraisonPrevue = this.getDateLivraisonPrevue();
                Object other$dateLivraisonPrevue = other.getDateLivraisonPrevue();
                if (this$dateLivraisonPrevue == null) {
                    if (other$dateLivraisonPrevue != null) {
                        return false;
                    }
                } else if (!this$dateLivraisonPrevue.equals(other$dateLivraisonPrevue)) {
                    return false;
                }

                Object this$dateLivraisonReelle = this.getDateLivraisonReelle();
                Object other$dateLivraisonReelle = other.getDateLivraisonReelle();
                if (this$dateLivraisonReelle == null) {
                    if (other$dateLivraisonReelle != null) {
                        return false;
                    }
                } else if (!this$dateLivraisonReelle.equals(other$dateLivraisonReelle)) {
                    return false;
                }
                
                // AJOUTÉ : Comparaison pour datePaiement
                Object this$datePaiement = this.getDatePaiement();
                Object other$datePaiement = other.getDatePaiement();
                if (this$datePaiement == null) {
                    if (other$datePaiement != null) {
                        return false;
                    }
                } else if (!this$datePaiement.equals(other$datePaiement)) {
                    return false;
                }

                Object this$senderName = this.getSenderName();
                Object other$senderName = other.getSenderName();
                if (this$senderName == null) {
                    if (other$senderName != null) {
                        return false;
                    }
                } else if (!this$senderName.equals(other$senderName)) {
                    return false;
                }

                Object this$senderPhone = this.getSenderPhone();
                Object other$senderPhone = other.getSenderPhone();
                if (this$senderPhone == null) {
                    if (other$senderPhone != null) {
                        return false;
                    }
                } else if (!this$senderPhone.equals(other$senderPhone)) {
                    return false;
                }

                Object this$senderEmail = this.getSenderEmail();
                Object other$senderEmail = other.getSenderEmail();
                if (this$senderEmail == null) {
                    if (other$senderEmail != null) {
                        return false;
                    }
                } else if (!this$senderEmail.equals(other$senderEmail)) {
                    return false;
                }

                Object this$villeOrigine = this.getVilleOrigine();
                Object other$villeOrigine = other.getVilleOrigine();
                if (this$villeOrigine == null) {
                    if (other$villeOrigine != null) {
                        return false;
                    }
                } else if (!this$villeOrigine.equals(other$villeOrigine)) {
                    return false;
                }

                Object this$quartierExpedition = this.getQuartierExpedition();
                Object other$quartierExpedition = other.getQuartierExpedition();
                if (this$quartierExpedition == null) {
                    if (other$quartierExpedition != null) {
                        return false;
                    }
                } else if (!this$quartierExpedition.equals(other$quartierExpedition)) {
                    return false;
                }

                Object this$nomDestinataire = this.getNomDestinataire();
                Object other$nomDestinataire = other.getNomDestinataire();
                if (this$nomDestinataire == null) {
                    if (other$nomDestinataire != null) {
                        return false;
                    }
                } else if (!this$nomDestinataire.equals(other$nomDestinataire)) {
                    return false;
                }

                Object this$numeroDestinataire = this.getNumeroDestinataire();
                Object other$numeroDestinataire = other.getNumeroDestinataire();
                if (this$numeroDestinataire == null) {
                    if (other$numeroDestinataire != null) {
                        return false;
                    }
                } else if (!this$numeroDestinataire.equals(other$numeroDestinataire)) {
                    return false;
                }

                Object this$emailDestinataire = this.getEmailDestinataire();
                Object other$emailDestinataire = other.getEmailDestinataire();
                if (this$emailDestinataire == null) {
                    if (other$emailDestinataire != null) {
                        return false;
                    }
                } else if (!this$emailDestinataire.equals(other$emailDestinataire)) {
                    return false;
                }

                Object this$villeDeDestination = this.getVilleDeDestination();
                Object other$villeDeDestination = other.getVilleDeDestination();
                if (this$villeDeDestination == null) {
                    if (other$villeDeDestination != null) {
                        return false;
                    }
                } else if (!this$villeDeDestination.equals(other$villeDeDestination)) {
                    return false;
                }

                Object this$quartierDestination = this.getQuartierDestination();
                Object other$quartierDestination = other.getQuartierDestination();
                if (this$quartierDestination == null) {
                    if (other$quartierDestination != null) {
                        return false;
                    }
                } else if (!this$quartierDestination.equals(other$quartierDestination)) {
                    return false;
                }

                Object this$modePaiement = this.getModePaiement();
                Object other$modePaiement = other.getModePaiement();
                if (this$modePaiement == null) {
                    if (other$modePaiement != null) {
                        return false;
                    }
                } else if (!this$modePaiement.equals(other$modePaiement)) {
                    return false;
                }

                Object this$paymentReference = this.getPaymentReference();
                Object other$paymentReference = other.getPaymentReference();
                if (this$paymentReference == null) {
                    if (other$paymentReference != null) {
                        return false;
                    }
                } else if (!this$paymentReference.equals(other$paymentReference)) {
                    return false;
                }

                Object this$codeValidation = this.getCodeValidation();
                Object other$codeValidation = other.getCodeValidation();
                if (this$codeValidation == null) {
                    if (other$codeValidation != null) {
                        return false;
                    }
                } else if (!this$codeValidation.equals(other$codeValidation)) {
                    return false;
                }

                Object this$agenceNom = this.getAgenceNom();
                Object other$agenceNom = other.getAgenceNom();
                if (this$agenceNom == null) {
                    if (other$agenceNom != null) {
                        return false;
                    }
                } else if (!this$agenceNom.equals(other$agenceNom)) {
                    return false;
                }

                Object this$assignedVoyageDescription = this.getAssignedVoyageDescription();
                Object other$assignedVoyageDescription = other.getAssignedVoyageDescription();
                if (this$assignedVoyageDescription == null) {
                    if (other$assignedVoyageDescription != null) {
                        return false;
                    }
                } else if (!this$assignedVoyageDescription.equals(other$assignedVoyageDescription)) {
                    return false;
                }

                Object this$assignedVoyageVehiculeImmatriculation = this.getAssignedVoyageVehiculeImmatriculation();
                Object other$assignedVoyageVehiculeImmatriculation = other.getAssignedVoyageVehiculeImmatriculation();
                if (this$assignedVoyageVehiculeImmatriculation == null) {
                    if (other$assignedVoyageVehiculeImmatriculation != null) {
                        return false;
                    }
                } else if (!this$assignedVoyageVehiculeImmatriculation.equals(other$assignedVoyageVehiculeImmatriculation)) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof ColisResponseDto;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $idColis = this.getIdColis();
        result = result * 59 + ($idColis == null ? 43 : $idColis.hashCode());
        Object $weight = this.getWeight();
        result = result * 59 + ($weight == null ? 43 : $weight.hashCode());
        Object $estimatedCost = this.getEstimatedCost();
        result = result * 59 + ($estimatedCost == null ? 43 : $estimatedCost.hashCode());
        Object $clientId = this.getClientId();
        result = result * 59 + ($clientId == null ? 43 : $clientId.hashCode());
        Object $agenceId = this.getAgenceId();
        result = result * 59 + ($agenceId == null ? 43 : $agenceId.hashCode());
        Object $assignedVoyageId = this.getAssignedVoyageId();
        result = result * 59 + ($assignedVoyageId == null ? 43 : $assignedVoyageId.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $dimensions = this.getDimensions();
        result = result * 59 + ($dimensions == null ? 43 : $dimensions.hashCode());
        Object $trackingNumber = this.getTrackingNumber();
        result = result * 59 + ($trackingNumber == null ? 43 : $trackingNumber.hashCode());
        Object $statut = this.getStatut();
        result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
        Object $dateEnregistrement = this.getDateEnregistrement();
        result = result * 59 + ($dateEnregistrement == null ? 43 : $dateEnregistrement.hashCode());
        Object $dateExpedition = this.getDateExpedition();
        result = result * 59 + ($dateExpedition == null ? 43 : $dateExpedition.hashCode());
        Object $dateArriveeAgenceDestination = this.getDateArriveeAgenceDestination();
        result = result * 59 + ($dateArriveeAgenceDestination == null ? 43 : $dateArriveeAgenceDestination.hashCode());
        Object $dateLivraisonPrevue = this.getDateLivraisonPrevue();
        result = result * 59 + ($dateLivraisonPrevue == null ? 43 : $dateLivraisonPrevue.hashCode());
        Object $dateLivraisonReelle = this.getDateLivraisonReelle();
        result = result * 59 + ($dateLivraisonReelle == null ? 43 : $dateLivraisonReelle.hashCode());
        // AJOUTÉ : HashCode pour datePaiement
        Object $datePaiement = this.getDatePaiement();
        result = result * 59 + ($datePaiement == null ? 43 : $datePaiement.hashCode());

        Object $senderName = this.getSenderName();
        result = result * 59 + ($senderName == null ? 43 : $senderName.hashCode());
        Object $senderPhone = this.getSenderPhone();
        result = result * 59 + ($senderPhone == null ? 43 : $senderPhone.hashCode());
        Object $senderEmail = this.getSenderEmail();
        result = result * 59 + ($senderEmail == null ? 43 : $senderEmail.hashCode());
        Object $villeOrigine = this.getVilleOrigine();
        result = result * 59 + ($villeOrigine == null ? 43 : $villeOrigine.hashCode());
        Object $quartierExpedition = this.getQuartierExpedition();
        result = result * 59 + ($quartierExpedition == null ? 43 : $quartierExpedition.hashCode());
        Object $nomDestinataire = this.getNomDestinataire();
        result = result * 59 + ($nomDestinataire == null ? 43 : $nomDestinataire.hashCode());
        Object $numeroDestinataire = this.getNumeroDestinataire();
        result = result * 59 + ($numeroDestinataire == null ? 43 : $numeroDestinataire.hashCode());
        Object $emailDestinataire = this.getEmailDestinataire();
        result = result * 59 + ($emailDestinataire == null ? 43 : $emailDestinataire.hashCode());
        Object $villeDeDestination = this.getVilleDeDestination();
        result = result * 59 + ($villeDeDestination == null ? 43 : $villeDeDestination.hashCode());
        Object $quartierDestination = this.getQuartierDestination();
        result = result * 59 + ($quartierDestination == null ? 43 : $quartierDestination.hashCode());
        Object $modePaiement = this.getModePaiement();
        result = result * 59 + ($modePaiement == null ? 43 : $modePaiement.hashCode());
        Object $paymentReference = this.getPaymentReference();
        result = result * 59 + ($paymentReference == null ? 43 : $paymentReference.hashCode());
        Object $codeValidation = this.getCodeValidation();
        result = result * 59 + ($codeValidation == null ? 43 : $codeValidation.hashCode());
        Object $agenceNom = this.getAgenceNom();
        result = result * 59 + ($agenceNom == null ? 43 : $agenceNom.hashCode());
        Object $assignedVoyageDescription = this.getAssignedVoyageDescription();
        result = result * 59 + ($assignedVoyageDescription == null ? 43 : $assignedVoyageDescription.hashCode());
        Object $assignedVoyageVehiculeImmatriculation = this.getAssignedVoyageVehiculeImmatriculation();
        result = result * 59 + ($assignedVoyageVehiculeImmatriculation == null ? 43 : $assignedVoyageVehiculeImmatriculation.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "ColisResponseDto(idColis=" + this.getIdColis() + ", description=" + this.getDescription() + ", weight=" + this.getWeight() + ", dimensions=" + this.getDimensions() + ", estimatedCost=" + this.getEstimatedCost() + ", trackingNumber=" + this.getTrackingNumber() + ", statut=" + this.getStatut() + ", dateEnregistrement=" + this.getDateEnregistrement() + ", dateExpedition=" + this.getDateExpedition() + ", dateArriveeAgenceDestination=" + this.getDateArriveeAgenceDestination() + ", dateLivraisonPrevue=" + this.getDateLivraisonPrevue() + ", dateLivraisonReelle=" + this.getDateLivraisonReelle() + ", datePaiement=" + this.getDatePaiement() + ", clientId=" + this.getClientId() + ", senderName=" + this.getSenderName() + ", senderPhone=" + this.getSenderPhone() + ", senderEmail=" + this.getSenderEmail() + ", villeOrigine=" + this.getVilleOrigine() + ", quartierExpedition=" + this.getQuartierExpedition() + ", nomDestinataire=" + this.getNomDestinataire() + ", numeroDestinataire=" + this.getNumeroDestinataire() + ", emailDestinataire=" + this.getEmailDestinataire() + ", villeDeDestination=" + this.getVilleDeDestination() + ", quartierDestination=" + this.getQuartierDestination() + ", modePaiement=" + this.getModePaiement() + ", paymentReference=" + this.getPaymentReference() + ", codeValidation=" + this.getCodeValidation() + ", agenceId=" + this.getAgenceId() + ", agenceNom=" + this.getAgenceNom() + ", assignedVoyageId=" + this.getAssignedVoyageId() + ", assignedVoyageDescription=" + this.getAssignedVoyageDescription() + ", assignedVoyageVehiculeImmatriculation=" + this.getAssignedVoyageVehiculeImmatriculation() + ")";
    }
}
