//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

import java.time.LocalDateTime;
import lombok.Generated;

public class ReservationResponseDto {
	private Long idReservation;
	private LocalDateTime dateReservation;
	private String siegeReserver;
	private String classe;
	private String statut;
	private String codeValidation;
	private String paymentReference;
	private Double montant;
	private Long clientId;
	private String clientNom;
	private Long voyageId;
	private String voyageDescription;
	private String vehiculeImmatriculation;

	@Generated
	public Long getIdReservation() {
		return this.idReservation;
	}

	@Generated
	public LocalDateTime getDateReservation() {
		return this.dateReservation;
	}

	@Generated
	public String getSiegeReserver() {
		return this.siegeReserver;
	}

	@Generated
	public String getClasse() {
		return this.classe;
	}

	@Generated
	public String getStatut() {
		return this.statut;
	}

	@Generated
	public String getCodeValidation() {
		return this.codeValidation;
	}

	@Generated
	public String getPaymentReference() {
		return this.paymentReference;
	}

	@Generated
	public Double getMontant() {
		return this.montant;
	}

	@Generated
	public Long getClientId() {
		return this.clientId;
	}

	@Generated
	public String getClientNom() {
		return this.clientNom;
	}

	@Generated
	public Long getVoyageId() {
		return this.voyageId;
	}

	@Generated
	public String getVoyageDescription() {
		return this.voyageDescription;
	}

	@Generated
	public String getVehiculeImmatriculation() {
		return this.vehiculeImmatriculation;
	}

	@Generated
	public void setIdReservation(final Long idReservation) {
		this.idReservation = idReservation;
	}

	@Generated
	public void setDateReservation(final LocalDateTime dateReservation) {
		this.dateReservation = dateReservation;
	}

	@Generated
	public void setSiegeReserver(final String siegeReserver) {
		this.siegeReserver = siegeReserver;
	}

	@Generated
	public void setClasse(final String classe) {
		this.classe = classe;
	}

	@Generated
	public void setStatut(final String statut) {
		this.statut = statut;
	}

	@Generated
	public void setCodeValidation(final String codeValidation) {
		this.codeValidation = codeValidation;
	}

	@Generated
	public void setPaymentReference(final String paymentReference) {
		this.paymentReference = paymentReference;
	}

	@Generated
	public void setMontant(final Double montant) {
		this.montant = montant;
	}

	@Generated
	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}

	@Generated
	public void setClientNom(final String clientNom) {
		this.clientNom = clientNom;
	}

	@Generated
	public void setVoyageId(final Long voyageId) {
		this.voyageId = voyageId;
	}

	@Generated
	public void setVoyageDescription(final String voyageDescription) {
		this.voyageDescription = voyageDescription;
	}

	@Generated
	public void setVehiculeImmatriculation(final String vehiculeImmatriculation) {
		this.vehiculeImmatriculation = vehiculeImmatriculation;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof ReservationResponseDto)) {
			return false;
		} else {
			ReservationResponseDto other = (ReservationResponseDto)o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				Object this$idReservation = this.getIdReservation();
				Object other$idReservation = other.getIdReservation();
				if (this$idReservation == null) {
					if (other$idReservation != null) {
						return false;
					}
				} else if (!this$idReservation.equals(other$idReservation)) {
					return false;
				}

				Object this$montant = this.getMontant();
				Object other$montant = other.getMontant();
				if (this$montant == null) {
					if (other$montant != null) {
						return false;
					}
				} else if (!this$montant.equals(other$montant)) {
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

				Object this$voyageId = this.getVoyageId();
				Object other$voyageId = other.getVoyageId();
				if (this$voyageId == null) {
					if (other$voyageId != null) {
						return false;
					}
				} else if (!this$voyageId.equals(other$voyageId)) {
					return false;
				}

				Object this$dateReservation = this.getDateReservation();
				Object other$dateReservation = other.getDateReservation();
				if (this$dateReservation == null) {
					if (other$dateReservation != null) {
						return false;
					}
				} else if (!this$dateReservation.equals(other$dateReservation)) {
					return false;
				}

				Object this$siegeReserver = this.getSiegeReserver();
				Object other$siegeReserver = other.getSiegeReserver();
				if (this$siegeReserver == null) {
					if (other$siegeReserver != null) {
						return false;
					}
				} else if (!this$siegeReserver.equals(other$siegeReserver)) {
					return false;
				}

				Object this$classe = this.getClasse();
				Object other$classe = other.getClasse();
				if (this$classe == null) {
					if (other$classe != null) {
						return false;
					}
				} else if (!this$classe.equals(other$classe)) {
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

				Object this$codeValidation = this.getCodeValidation();
				Object other$codeValidation = other.getCodeValidation();
				if (this$codeValidation == null) {
					if (other$codeValidation != null) {
						return false;
					}
				} else if (!this$codeValidation.equals(other$codeValidation)) {
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

				Object this$clientNom = this.getClientNom();
				Object other$clientNom = other.getClientNom();
				if (this$clientNom == null) {
					if (other$clientNom != null) {
						return false;
					}
				} else if (!this$clientNom.equals(other$clientNom)) {
					return false;
				}

				Object this$voyageDescription = this.getVoyageDescription();
				Object other$voyageDescription = other.getVoyageDescription();
				if (this$voyageDescription == null) {
					if (other$voyageDescription != null) {
						return false;
					}
				} else if (!this$voyageDescription.equals(other$voyageDescription)) {
					return false;
				}

				Object this$vehiculeImmatriculation = this.getVehiculeImmatriculation();
				Object other$vehiculeImmatriculation = other.getVehiculeImmatriculation();
				if (this$vehiculeImmatriculation == null) {
					if (other$vehiculeImmatriculation != null) {
						return false;
					}
				} else if (!this$vehiculeImmatriculation.equals(other$vehiculeImmatriculation)) {
					return false;
				}

				return true;
			}
		}
	}

	@Generated
	protected boolean canEqual(final Object other) {
		return other instanceof ReservationResponseDto;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $idReservation = this.getIdReservation();
		result = result * 59 + ($idReservation == null ? 43 : $idReservation.hashCode());
		Object $montant = this.getMontant();
		result = result * 59 + ($montant == null ? 43 : $montant.hashCode());
		Object $clientId = this.getClientId();
		result = result * 59 + ($clientId == null ? 43 : $clientId.hashCode());
		Object $voyageId = this.getVoyageId();
		result = result * 59 + ($voyageId == null ? 43 : $voyageId.hashCode());
		Object $dateReservation = this.getDateReservation();
		result = result * 59 + ($dateReservation == null ? 43 : $dateReservation.hashCode());
		Object $siegeReserver = this.getSiegeReserver();
		result = result * 59 + ($siegeReserver == null ? 43 : $siegeReserver.hashCode());
		Object $classe = this.getClasse();
		result = result * 59 + ($classe == null ? 43 : $classe.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		Object $codeValidation = this.getCodeValidation();
		result = result * 59 + ($codeValidation == null ? 43 : $codeValidation.hashCode());
		Object $paymentReference = this.getPaymentReference();
		result = result * 59 + ($paymentReference == null ? 43 : $paymentReference.hashCode());
		Object $clientNom = this.getClientNom();
		result = result * 59 + ($clientNom == null ? 43 : $clientNom.hashCode());
		Object $voyageDescription = this.getVoyageDescription();
		result = result * 59 + ($voyageDescription == null ? 43 : $voyageDescription.hashCode());
		Object $vehiculeImmatriculation = this.getVehiculeImmatriculation();
		result = result * 59 + ($vehiculeImmatriculation == null ? 43 : $vehiculeImmatriculation.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "ReservationResponseDto(idReservation=" + this.getIdReservation() + ", dateReservation=" + this.getDateReservation() + ", siegeReserver=" + this.getSiegeReserver() + ", classe=" + this.getClasse() + ", statut=" + this.getStatut() + ", codeValidation=" + this.getCodeValidation() + ", paymentReference=" + this.getPaymentReference() + ", montant=" + this.getMontant() + ", clientId=" + this.getClientId() + ", clientNom=" + this.getClientNom() + ", voyageId=" + this.getVoyageId() + ", voyageDescription=" + this.getVoyageDescription() + ", vehiculeImmatriculation=" + this.getVehiculeImmatriculation() + ")";
	}
}
