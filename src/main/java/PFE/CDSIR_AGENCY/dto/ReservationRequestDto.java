//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;

public class ReservationRequestDto {
	private @NotNull(
			message = "L'ID du voyage est requis"
	) Long voyageId;
	private @NotBlank(
			message = "Le siège à réserver est requis"
	) String siegeReserver;
	private @NotBlank(
			message = "La classe est requise"
	) String classe;
	private @NotBlank(
			message = "Le mode de paiement est requis"
	) String modePaiement;
	private String mobileMoneyType;
	private String paymentReference;
	private @NotNull(
			message = "Le montant est requis"
	) Double montant;
	private Long clientId;
	private String clientEmail;
	private String clientNom;
	private String clientPrenom;
	private String clientTelephone;

	@Generated
	public Long getVoyageId() {
		return this.voyageId;
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
	public String getModePaiement() {
		return this.modePaiement;
	}

	@Generated
	public String getMobileMoneyType() {
		return this.mobileMoneyType;
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
	public String getClientEmail() {
		return this.clientEmail;
	}

	@Generated
	public String getClientNom() {
		return this.clientNom;
	}

	@Generated
	public String getClientPrenom() {
		return this.clientPrenom;
	}

	@Generated
	public String getClientTelephone() {
		return this.clientTelephone;
	}

	@Generated
	public void setVoyageId(final Long voyageId) {
		this.voyageId = voyageId;
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
	public void setModePaiement(final String modePaiement) {
		this.modePaiement = modePaiement;
	}

	@Generated
	public void setMobileMoneyType(final String mobileMoneyType) {
		this.mobileMoneyType = mobileMoneyType;
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
	public void setClientEmail(final String clientEmail) {
		this.clientEmail = clientEmail;
	}

	@Generated
	public void setClientNom(final String clientNom) {
		this.clientNom = clientNom;
	}

	@Generated
	public void setClientPrenom(final String clientPrenom) {
		this.clientPrenom = clientPrenom;
	}

	@Generated
	public void setClientTelephone(final String clientTelephone) {
		this.clientTelephone = clientTelephone;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof ReservationRequestDto)) {
			return false;
		} else {
			ReservationRequestDto other = (ReservationRequestDto)o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				Object this$voyageId = this.getVoyageId();
				Object other$voyageId = other.getVoyageId();
				if (this$voyageId == null) {
					if (other$voyageId != null) {
						return false;
					}
				} else if (!this$voyageId.equals(other$voyageId)) {
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

				Object this$modePaiement = this.getModePaiement();
				Object other$modePaiement = other.getModePaiement();
				if (this$modePaiement == null) {
					if (other$modePaiement != null) {
						return false;
					}
				} else if (!this$modePaiement.equals(other$modePaiement)) {
					return false;
				}

				Object this$mobileMoneyType = this.getMobileMoneyType();
				Object other$mobileMoneyType = other.getMobileMoneyType();
				if (this$mobileMoneyType == null) {
					if (other$mobileMoneyType != null) {
						return false;
					}
				} else if (!this$mobileMoneyType.equals(other$mobileMoneyType)) {
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

				Object this$clientEmail = this.getClientEmail();
				Object other$clientEmail = other.getClientEmail();
				if (this$clientEmail == null) {
					if (other$clientEmail != null) {
						return false;
					}
				} else if (!this$clientEmail.equals(other$clientEmail)) {
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

				Object this$clientPrenom = this.getClientPrenom();
				Object other$clientPrenom = other.getClientPrenom();
				if (this$clientPrenom == null) {
					if (other$clientPrenom != null) {
						return false;
					}
				} else if (!this$clientPrenom.equals(other$clientPrenom)) {
					return false;
				}

				Object this$clientTelephone = this.getClientTelephone();
				Object other$clientTelephone = other.getClientTelephone();
				if (this$clientTelephone == null) {
					if (other$clientTelephone != null) {
						return false;
					}
				} else if (!this$clientTelephone.equals(other$clientTelephone)) {
					return false;
				}

				return true;
			}
		}
	}

	@Generated
	protected boolean canEqual(final Object other) {
		return other instanceof ReservationRequestDto;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $voyageId = this.getVoyageId();
		result = result * 59 + ($voyageId == null ? 43 : $voyageId.hashCode());
		Object $montant = this.getMontant();
		result = result * 59 + ($montant == null ? 43 : $montant.hashCode());
		Object $clientId = this.getClientId();
		result = result * 59 + ($clientId == null ? 43 : $clientId.hashCode());
		Object $siegeReserver = this.getSiegeReserver();
		result = result * 59 + ($siegeReserver == null ? 43 : $siegeReserver.hashCode());
		Object $classe = this.getClasse();
		result = result * 59 + ($classe == null ? 43 : $classe.hashCode());
		Object $modePaiement = this.getModePaiement();
		result = result * 59 + ($modePaiement == null ? 43 : $modePaiement.hashCode());
		Object $mobileMoneyType = this.getMobileMoneyType();
		result = result * 59 + ($mobileMoneyType == null ? 43 : $mobileMoneyType.hashCode());
		Object $paymentReference = this.getPaymentReference();
		result = result * 59 + ($paymentReference == null ? 43 : $paymentReference.hashCode());
		Object $clientEmail = this.getClientEmail();
		result = result * 59 + ($clientEmail == null ? 43 : $clientEmail.hashCode());
		Object $clientNom = this.getClientNom();
		result = result * 59 + ($clientNom == null ? 43 : $clientNom.hashCode());
		Object $clientPrenom = this.getClientPrenom();
		result = result * 59 + ($clientPrenom == null ? 43 : $clientPrenom.hashCode());
		Object $clientTelephone = this.getClientTelephone();
		result = result * 59 + ($clientTelephone == null ? 43 : $clientTelephone.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "ReservationRequestDto(voyageId=" + this.getVoyageId() + ", siegeReserver=" + this.getSiegeReserver() + ", classe=" + this.getClasse() + ", modePaiement=" + this.getModePaiement() + ", mobileMoneyType=" + this.getMobileMoneyType() + ", paymentReference=" + this.getPaymentReference() + ", montant=" + this.getMontant() + ", clientId=" + this.getClientId() + ", clientEmail=" + this.getClientEmail() + ", clientNom=" + this.getClientNom() + ", clientPrenom=" + this.getClientPrenom() + ", clientTelephone=" + this.getClientTelephone() + ")";
	}
}
