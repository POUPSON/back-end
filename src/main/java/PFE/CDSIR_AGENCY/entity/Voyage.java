//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- N'oubliez pas cet import !
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
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI
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
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI
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
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI
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
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI
	private @NotNull(
			message = "Le voyage doit être associé à une agence"
	) Agence agence;

	@OneToMany(
			mappedBy = "voyage",
			cascade = {CascadeType.ALL},
			orphanRemoval = true
	)
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI
	private List<Reservation> reservations;

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
	public void setId(final Long id) {
		this.id = id;
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
	public void setTrajet(final Trajet trajet) {
		this.trajet = trajet;
	}

	@Generated
	public void setHoraire(final Horaire horaire) {
		this.horaire = horaire;
	}

	@Generated
	public void setVehicule(final Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	@Generated
	public void setAgence(final Agence agence) {
		this.agence = agence;
	}

	@Generated
	public void setReservations(final List<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Voyage)) {
			return false;
		} else {
			Voyage other = (Voyage)o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				Object this$id = this.getId();
				Object other$id = other.getId();
				if (this$id == null) {
					if (other$id != null) {
						return false;
					}
				} else if (!this$id.equals(other$id)) {
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

				Object this$trajet = this.getTrajet();
				Object other$trajet = other.getTrajet();
				if (this$trajet == null) {
					if (other$trajet != null) {
						return false;
					}
				} else if (!this$trajet.equals(other$trajet)) {
					return false;
				}

				Object this$horaire = this.getHoraire();
				Object other$horaire = other.getHoraire();
				if (this$horaire == null) {
					if (other$horaire != null) {
						return false;
					}
				} else if (!this$horaire.equals(other$horaire)) {
					return false;
				}

				Object this$vehicule = this.getVehicule();
				Object other$vehicule = other.getVehicule();
				if (this$vehicule == null) {
					if (other$vehicule != null) {
						return false;
					}
				} else if (!this$vehicule.equals(other$vehicule)) {
					return false;
				}

				Object this$agence = this.getAgence();
				Object other$agence = other.getAgence();
				if (this$agence == null) {
					if (other$agence != null) {
						return false;
					}
				} else if (!this$agence.equals(other$agence)) {
					return false;
				}

				return true;
			}
		}
	}

	@Generated
	protected boolean canEqual(final Object other) {
		return other instanceof Voyage;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $id = this.getId();
		result = result * 59 + ($id == null ? 43 : $id.hashCode());
		Object $dateDepart = this.getDateDepart();
		result = result * 59 + ($dateDepart == null ? 43 : $dateDepart.hashCode());
		Object $dateArrivee = this.getDateArrivee();
		result = result * 59 + ($dateArrivee == null ? 43 : $dateArrivee.hashCode());
		Object $prix = this.getPrix();
		result = result * 59 + ($prix == null ? 43 : $prix.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		Object $trajet = this.getTrajet();
		result = result * 59 + ($trajet == null ? 43 : $trajet.hashCode());
		Object $horaire = this.getHoraire();
		result = result * 59 + ($horaire == null ? 43 : $horaire.hashCode());
		Object $vehicule = this.getVehicule();
		result = result * 59 + ($vehicule == null ? 43 : $vehicule.hashCode());
		Object $agence = this.getAgence();
		result = result * 59 + ($agence == null ? 43 : $agence.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "Voyage(id=" + this.getId() + ", dateDepart=" + this.getDateDepart() + ", dateArrivee=" + this.getDateArrivee() + ", prix=" + this.getPrix() + ", statut=" + this.getStatut() + ", trajet=" + this.getTrajet() + ", horaire=" + this.getHoraire() + ", vehicule=" + this.getVehicule() + ", agence=" + this.getAgence() + ")";
	}
}
