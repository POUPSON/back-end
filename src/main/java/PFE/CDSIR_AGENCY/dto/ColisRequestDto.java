//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Generated;

public class ColisRequestDto {
	private @NotNull(
			message = "L'ID de l'agence est requis"
	) @Positive(
			message = "L'ID de l'agence doit être un nombre positif"
	) Long agenceId;
	private Long clientId;
	private @NotBlank(
			message = "La description du colis est requise"
	) @Size(
			max = 255,
			message = "La description ne peut pas dépasser 255 caractères"
	) String description;
	private @NotNull(
			message = "Le poids du colis est requis"
	) @Positive(
			message = "Le poids doit être un nombre positif"
	) Double weight;
	private @Size(
			max = 100,
			message = "Les dimensions ne peuvent pas dépasser 100 caractères"
	) String dimensions;
	private @NotNull(
			message = "Le coût estimé est requis"
	) @Positive(
			message = "Le coût estimé doit être un nombre positif"
	) Double estimatedCost;
	private @NotBlank(
			message = "Le nom de l'expéditeur est requis"
	) @Size(
			max = 100,
			message = "Le nom de l'expéditeur ne peut pas dépasser 100 caractères"
	) String senderName;
	private @NotBlank(
			message = "Le téléphone de l'expéditeur est requis"
	) @Size(
			max = 20,
			message = "Le téléphone de l'expéditeur ne peut pas dépasser 20 caractères"
	) String senderPhone;
	private @Email(
			message = "L'adresse email de l'expéditeur doit être valide"
	) @Size(
			max = 100,
			message = "L'email de l'expéditeur ne peut pas dépasser 100 caractères"
	) String senderEmail;
	private @NotBlank(
			message = "La ville d'origine est requise"
	) @Size(
			max = 100,
			message = "La ville d'origine ne peut pas dépasser 100 caractères"
	) String originCity;
	private @Size(
			max = 100,
			message = "Le quartier d'expédition ne peut pas dépasser 100 caractères"
	) String originNeighborhood;
	private @NotBlank(
			message = "Le nom du destinataire est requis"
	) @Size(
			max = 100,
			message = "Le nom du destinataire ne peut pas dépasser 100 caractères"
	) String receiverName;
	private @NotBlank(
			message = "Le téléphone du destinataire est requis"
	) @Size(
			max = 20,
			message = "Le téléphone du destinataire ne peut pas dépasser 20 caractères"
	) String receiverPhone;
	private @Email(
			message = "L'adresse email du destinataire doit être valide"
	) @Size(
			max = 100,
			message = "L'email du destinataire ne peut pas dépasser 100 caractères"
	) String receiverEmail;
	private @NotBlank(
			message = "La ville de destination est requise"
	) @Size(
			max = 100,
			message = "La ville de destination ne peut pas dépasser 100 caractères"
	) String destinationCity;
	private @Size(
			max = 100,
			message = "Le quartier de destination ne peut pas dépasser 100 caractères"
	) String destinationNeighborhood;
	private @NotBlank(
			message = "Le mode de paiement est requis"
	) @Size(
			max = 50,
			message = "Le mode de paiement ne peut pas dépasser 50 caractères"
	) String modePaiement;
	private @Size(
			max = 100,
			message = "La référence de paiement ne peut pas dépasser 100 caractères"
	) String paymentReference;
	private LocalDateTime plannedDeliveryDate;

	@Generated
	public Long getAgenceId() {
		return this.agenceId;
	}

	@Generated
	public Long getClientId() {
		return this.clientId;
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
	public String getOriginCity() {
		return this.originCity;
	}

	@Generated
	public String getOriginNeighborhood() {
		return this.originNeighborhood;
	}

	@Generated
	public String getReceiverName() {
		return this.receiverName;
	}

	@Generated
	public String getReceiverPhone() {
		return this.receiverPhone;
	}

	@Generated
	public String getReceiverEmail() {
		return this.receiverEmail;
	}

	@Generated
	public String getDestinationCity() {
		return this.destinationCity;
	}

	@Generated
	public String getDestinationNeighborhood() {
		return this.destinationNeighborhood;
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
	public LocalDateTime getPlannedDeliveryDate() {
		return this.plannedDeliveryDate;
	}

	@Generated
	public void setAgenceId(final Long agenceId) {
		this.agenceId = agenceId;
	}

	@Generated
	public void setClientId(final Long clientId) {
		this.clientId = clientId;
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
	public void setOriginCity(final String originCity) {
		this.originCity = originCity;
	}

	@Generated
	public void setOriginNeighborhood(final String originNeighborhood) {
		this.originNeighborhood = originNeighborhood;
	}

	@Generated
	public void setReceiverName(final String receiverName) {
		this.receiverName = receiverName;
	}

	@Generated
	public void setReceiverPhone(final String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	@Generated
	public void setReceiverEmail(final String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	@Generated
	public void setDestinationCity(final String destinationCity) {
		this.destinationCity = destinationCity;
	}

	@Generated
	public void setDestinationNeighborhood(final String destinationNeighborhood) {
		this.destinationNeighborhood = destinationNeighborhood;
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
	public void setPlannedDeliveryDate(final LocalDateTime plannedDeliveryDate) {
		this.plannedDeliveryDate = plannedDeliveryDate;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof ColisRequestDto)) {
			return false;
		} else {
			ColisRequestDto other = (ColisRequestDto)o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				Object this$agenceId = this.getAgenceId();
				Object other$agenceId = other.getAgenceId();
				if (this$agenceId == null) {
					if (other$agenceId != null) {
						return false;
					}
				} else if (!this$agenceId.equals(other$agenceId)) {
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

				Object this$originCity = this.getOriginCity();
				Object other$originCity = other.getOriginCity();
				if (this$originCity == null) {
					if (other$originCity != null) {
						return false;
					}
				} else if (!this$originCity.equals(other$originCity)) {
					return false;
				}

				Object this$originNeighborhood = this.getOriginNeighborhood();
				Object other$originNeighborhood = other.getOriginNeighborhood();
				if (this$originNeighborhood == null) {
					if (other$originNeighborhood != null) {
						return false;
					}
				} else if (!this$originNeighborhood.equals(other$originNeighborhood)) {
					return false;
				}

				Object this$receiverName = this.getReceiverName();
				Object other$receiverName = other.getReceiverName();
				if (this$receiverName == null) {
					if (other$receiverName != null) {
						return false;
					}
				} else if (!this$receiverName.equals(other$receiverName)) {
					return false;
				}

				Object this$receiverPhone = this.getReceiverPhone();
				Object other$receiverPhone = other.getReceiverPhone();
				if (this$receiverPhone == null) {
					if (other$receiverPhone != null) {
						return false;
					}
				} else if (!this$receiverPhone.equals(other$receiverPhone)) {
					return false;
				}

				Object this$receiverEmail = this.getReceiverEmail();
				Object other$receiverEmail = other.getReceiverEmail();
				if (this$receiverEmail == null) {
					if (other$receiverEmail != null) {
						return false;
					}
				} else if (!this$receiverEmail.equals(other$receiverEmail)) {
					return false;
				}

				Object this$destinationCity = this.getDestinationCity();
				Object other$destinationCity = other.getDestinationCity();
				if (this$destinationCity == null) {
					if (other$destinationCity != null) {
						return false;
					}
				} else if (!this$destinationCity.equals(other$destinationCity)) {
					return false;
				}

				Object this$destinationNeighborhood = this.getDestinationNeighborhood();
				Object other$destinationNeighborhood = other.getDestinationNeighborhood();
				if (this$destinationNeighborhood == null) {
					if (other$destinationNeighborhood != null) {
						return false;
					}
				} else if (!this$destinationNeighborhood.equals(other$destinationNeighborhood)) {
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

				Object this$plannedDeliveryDate = this.getPlannedDeliveryDate();
				Object other$plannedDeliveryDate = other.getPlannedDeliveryDate();
				if (this$plannedDeliveryDate == null) {
					if (other$plannedDeliveryDate != null) {
						return false;
					}
				} else if (!this$plannedDeliveryDate.equals(other$plannedDeliveryDate)) {
					return false;
				}

				return true;
			}
		}
	}

	@Generated
	protected boolean canEqual(final Object other) {
		return other instanceof ColisRequestDto;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $agenceId = this.getAgenceId();
		result = result * 59 + ($agenceId == null ? 43 : $agenceId.hashCode());
		Object $clientId = this.getClientId();
		result = result * 59 + ($clientId == null ? 43 : $clientId.hashCode());
		Object $weight = this.getWeight();
		result = result * 59 + ($weight == null ? 43 : $weight.hashCode());
		Object $estimatedCost = this.getEstimatedCost();
		result = result * 59 + ($estimatedCost == null ? 43 : $estimatedCost.hashCode());
		Object $description = this.getDescription();
		result = result * 59 + ($description == null ? 43 : $description.hashCode());
		Object $dimensions = this.getDimensions();
		result = result * 59 + ($dimensions == null ? 43 : $dimensions.hashCode());
		Object $senderName = this.getSenderName();
		result = result * 59 + ($senderName == null ? 43 : $senderName.hashCode());
		Object $senderPhone = this.getSenderPhone();
		result = result * 59 + ($senderPhone == null ? 43 : $senderPhone.hashCode());
		Object $senderEmail = this.getSenderEmail();
		result = result * 59 + ($senderEmail == null ? 43 : $senderEmail.hashCode());
		Object $originCity = this.getOriginCity();
		result = result * 59 + ($originCity == null ? 43 : $originCity.hashCode());
		Object $originNeighborhood = this.getOriginNeighborhood();
		result = result * 59 + ($originNeighborhood == null ? 43 : $originNeighborhood.hashCode());
		Object $receiverName = this.getReceiverName();
		result = result * 59 + ($receiverName == null ? 43 : $receiverName.hashCode());
		Object $receiverPhone = this.getReceiverPhone();
		result = result * 59 + ($receiverPhone == null ? 43 : $receiverPhone.hashCode());
		Object $receiverEmail = this.getReceiverEmail();
		result = result * 59 + ($receiverEmail == null ? 43 : $receiverEmail.hashCode());
		Object $destinationCity = this.getDestinationCity();
		result = result * 59 + ($destinationCity == null ? 43 : $destinationCity.hashCode());
		Object $destinationNeighborhood = this.getDestinationNeighborhood();
		result = result * 59 + ($destinationNeighborhood == null ? 43 : $destinationNeighborhood.hashCode());
		Object $modePaiement = this.getModePaiement();
		result = result * 59 + ($modePaiement == null ? 43 : $modePaiement.hashCode());
		Object $paymentReference = this.getPaymentReference();
		result = result * 59 + ($paymentReference == null ? 43 : $paymentReference.hashCode());
		Object $plannedDeliveryDate = this.getPlannedDeliveryDate();
		result = result * 59 + ($plannedDeliveryDate == null ? 43 : $plannedDeliveryDate.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "ColisRequestDto(agenceId=" + this.getAgenceId() + ", clientId=" + this.getClientId() + ", description=" + this.getDescription() + ", weight=" + this.getWeight() + ", dimensions=" + this.getDimensions() + ", estimatedCost=" + this.getEstimatedCost() + ", senderName=" + this.getSenderName() + ", senderPhone=" + this.getSenderPhone() + ", senderEmail=" + this.getSenderEmail() + ", originCity=" + this.getOriginCity() + ", originNeighborhood=" + this.getOriginNeighborhood() + ", receiverName=" + this.getReceiverName() + ", receiverPhone=" + this.getReceiverPhone() + ", receiverEmail=" + this.getReceiverEmail() + ", destinationCity=" + this.getDestinationCity() + ", destinationNeighborhood=" + this.getDestinationNeighborhood() + ", modePaiement=" + this.getModePaiement() + ", paymentReference=" + this.getPaymentReference() + ", plannedDeliveryDate=" + this.getPlannedDeliveryDate() + ")";
	}
}
