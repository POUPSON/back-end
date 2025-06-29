//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Generated;

public class VoyageRequestDto {
	private @NotNull(
			message = "L'ID du trajet est requis"
	) Long trajetId;
	private @NotNull(
			message = "L'ID de l'horaire est requis"
	) Long horaireId;
	private @NotNull(
			message = "L'ID du véhicule est requis"
	) Long vehiculeId;
	private @NotNull(
			message = "L'ID de l'agence est requis"
	) Long agenceId;
	private @NotNull(
			message = "La date de départ est requise"
	) @FutureOrPresent(
			message = "La date de départ ne peut pas être dans le passé"
	) LocalDate dateDepart;
	private @NotNull(
			message = "La date d'arrivée est requise"
	) @FutureOrPresent(
			message = "La date d'arrivée ne peut pas être dans le passé"
	) LocalDate dateArrivee;
	private @NotNull(
			message = "Le prix est requis"
	) @DecimalMin(
			value = "0.01",
			message = "Le prix doit être supérieur à 0"
	) BigDecimal prix;
	private @Size(
			max = 20,
			message = "Le statut ne doit pas dépasser 20 caractères"
	) String statut;

	@Generated
	public Long getTrajetId() {
		return this.trajetId;
	}

	@Generated
	public Long getHoraireId() {
		return this.horaireId;
	}

	@Generated
	public Long getVehiculeId() {
		return this.vehiculeId;
	}

	@Generated
	public Long getAgenceId() {
		return this.agenceId;
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
	public void setTrajetId(final Long trajetId) {
		this.trajetId = trajetId;
	}

	@Generated
	public void setHoraireId(final Long horaireId) {
		this.horaireId = horaireId;
	}

	@Generated
	public void setVehiculeId(final Long vehiculeId) {
		this.vehiculeId = vehiculeId;
	}

	@Generated
	public void setAgenceId(final Long agenceId) {
		this.agenceId = agenceId;
	}

	@Generated
	public void setDateDepart(final LocalDate dateDepart) {
		this.dateDepart = dateDepart;
	}

	@Generated
	public void setDateArrivee(final LocalDate dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	@Generated
	public void setPrix(final BigDecimal prix) {
		this.prix = prix;
	}

	@Generated
	public void setStatut(final String statut) {
		this.statut = statut;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof VoyageRequestDto)) {
			return false;
		} else {
			VoyageRequestDto other = (VoyageRequestDto)o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				Object this$trajetId = this.getTrajetId();
				Object other$trajetId = other.getTrajetId();
				if (this$trajetId == null) {
					if (other$trajetId != null) {
						return false;
					}
				} else if (!this$trajetId.equals(other$trajetId)) {
					return false;
				}

				Object this$horaireId = this.getHoraireId();
				Object other$horaireId = other.getHoraireId();
				if (this$horaireId == null) {
					if (other$horaireId != null) {
						return false;
					}
				} else if (!this$horaireId.equals(other$horaireId)) {
					return false;
				}

				Object this$vehiculeId = this.getVehiculeId();
				Object other$vehiculeId = other.getVehiculeId();
				if (this$vehiculeId == null) {
					if (other$vehiculeId != null) {
						return false;
					}
				} else if (!this$vehiculeId.equals(other$vehiculeId)) {
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

				Object this$dateDepart = this.getDateDepart();
				Object other$dateDepart = other.getDateDepart();
				if (this$dateDepart == null) {
					if (other$dateDepart != null) {
						return false;
					}
				} else if (!this$dateDepart.equals(other$dateDepart)) {
					return false;
				}

				Object this$dateArrivee = this.getDateArrivee();
				Object other$dateArrivee = other.getDateArrivee();
				if (this$dateArrivee == null) {
					if (other$dateArrivee != null) {
						return false;
					}
				} else if (!this$dateArrivee.equals(other$dateArrivee)) {
					return false;
				}

				Object this$prix = this.getPrix();
				Object other$prix = other.getPrix();
				if (this$prix == null) {
					if (other$prix != null) {
						return false;
					}
				} else if (!this$prix.equals(other$prix)) {
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

				return true;
			}
		}
	}

	@Generated
	protected boolean canEqual(final Object other) {
		return other instanceof VoyageRequestDto;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $trajetId = this.getTrajetId();
		result = result * 59 + ($trajetId == null ? 43 : $trajetId.hashCode());
		Object $horaireId = this.getHoraireId();
		result = result * 59 + ($horaireId == null ? 43 : $horaireId.hashCode());
		Object $vehiculeId = this.getVehiculeId();
		result = result * 59 + ($vehiculeId == null ? 43 : $vehiculeId.hashCode());
		Object $agenceId = this.getAgenceId();
		result = result * 59 + ($agenceId == null ? 43 : $agenceId.hashCode());
		Object $dateDepart = this.getDateDepart();
		result = result * 59 + ($dateDepart == null ? 43 : $dateDepart.hashCode());
		Object $dateArrivee = this.getDateArrivee();
		result = result * 59 + ($dateArrivee == null ? 43 : $dateArrivee.hashCode());
		Object $prix = this.getPrix();
		result = result * 59 + ($prix == null ? 43 : $prix.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "VoyageRequestDto(trajetId=" + this.getTrajetId() + ", horaireId=" + this.getHoraireId() + ", vehiculeId=" + this.getVehiculeId() + ", agenceId=" + this.getAgenceId() + ", dateDepart=" + this.getDateDepart() + ", dateArrivee=" + this.getDateArrivee() + ", prix=" + this.getPrix() + ", statut=" + this.getStatut() + ")";
	}
}
