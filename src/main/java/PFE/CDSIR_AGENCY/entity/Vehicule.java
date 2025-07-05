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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Generated;

@Entity
@Table(
		name = "vehicule"
)
public class Vehicule {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	@Column(
			name = "id_vehicule"
	)
	private Long id;
	@Column(
			name = "marque",
			length = 50
	)
	private @NotBlank(
			message = "La marque est requise"
	) @Size(
			max = 50,
			message = "La marque ne doit pas dépasser 50 caractères"
	) String marque;
	@Column(
			name = "modele",
			length = 50
	)
	private @NotBlank(
			message = "Le modèle est requis"
	) @Size(
			max = 50,
			message = "Le modèle ne doit pas dépasser 50 caractères"
	) String modele;
	@Column(
			name = "annee_fabrication"
	)
	private @NotNull(
			message = "L'année de fabrication est requise"
	) @Min(
			value = 1900L,
			message = "L'année de fabrication doit être valide"
	) Integer anneeFabrication;
	@Column(
			name = "capacite"
	)
	private @NotNull(
			message = "La capacité est requise"
	) @Min(
			value = 1L,
			message = "La capacité doit être au moins 1"
	) Integer capacite;
	@Column(
			name = "immatriculation",
			length = 20,
			unique = true
	)
	private @NotBlank(
			message = "L'immatriculation est requise"
	) @Size(
			max = 20,
			message = "L'immatriculation ne doit pas dépasser 20 caractères"
	) String immatriculation;
	@Column(
			name = "statut",
			length = 20
	)
	private @Size(
			max = 20,
			message = "Le statut ne doit pas dépasser 20 caractères"
	) String statut;

	@ManyToOne(
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "id_agence",
			nullable = false
	)
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI pour la relation avec Agence
	private @NotNull(
			message = "Le véhicule doit être associé à une agence"
	) Agence agence;

	@OneToMany(
			mappedBy = "vehicule",
			cascade = {CascadeType.ALL},
			orphanRemoval = true
	)
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI pour la relation avec Voyage
	private List<Voyage> voyages;

	@Generated
	public Long getId() {
		return this.id;
	}

	@Generated
	public String getMarque() {
		return this.marque;
	}

	@Generated
	public String getModele() {
		return this.modele;
	}

	@Generated
	public Integer getAnneeFabrication() {
		return this.anneeFabrication;
	}

	@Generated
	public Integer getCapacite() {
		return this.capacite;
	}

	@Generated
	public String getImmatriculation() {
		return this.immatriculation;
	}

	@Generated
	public String getStatut() {
		return this.statut;
	}

	@Generated
	public Agence getAgence() {
		return this.agence;
	}

	@Generated
	public List<Voyage> getVoyages() {
		return this.voyages;
	}

	@Generated
	public void setId(final Long id) {
		this.id = id;
	}

	@Generated
	public void setMarque(final String marque) {
		this.marque = marque;
	}

	@Generated
	public void setModele(final String modele) {
		this.modele = modele;
	}

	@Generated
	public void setAnneeFabrication(final Integer anneeFabrication) {
		this.anneeFabrication = anneeFabrication;
	}

	@Generated
	public void setCapacite(final Integer capacite) {
		this.capacite = capacite;
	}

	@Generated
	public void setImmatriculation(final String immatriculation) {
		this.immatriculation = immatriculation;
	}

	@Generated
	public void setStatut(final String statut) {
		this.statut = statut;
	}

	@Generated
	public void setAgence(final Agence agence) {
		this.agence = agence;
	}

	@Generated
	public void setVoyages(final List<Voyage> voyages) {
		this.voyages = voyages;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Vehicule)) {
			return false;
		} else {
			Vehicule other = (Vehicule)o;
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

				Object this$anneeFabrication = this.getAnneeFabrication();
				Object other$anneeFabrication = other.getAnneeFabrication();
				if (this$anneeFabrication == null) {
					if (other$anneeFabrication != null) {
						return false;
					}
				} else if (!this$anneeFabrication.equals(other$anneeFabrication)) {
					return false;
				}

				Object this$capacite = this.getCapacite();
				Object other$capacite = other.getCapacite();
				if (this$capacite == null) {
					if (other$capacite != null) {
						return false;
					}
				} else if (!this$capacite.equals(other$capacite)) {
					return false;
				}

				Object this$marque = this.getMarque();
				Object other$marque = other.getMarque();
				if (this$marque == null) {
					if (other$marque != null) {
						return false;
					}
				} else if (!this$marque.equals(other$marque)) {
					return false;
				}

				Object this$modele = this.getModele();
				Object other$modele = other.getModele();
				if (this$modele == null) {
					if (other$modele != null) {
						return false;
					}
				} else if (!this$modele.equals(other$modele)) {
					return false;
				}

				Object this$immatriculation = this.getImmatriculation();
				Object other$immatriculation = other.getImmatriculation();
				if (this$immatriculation == null) {
					if (other$immatriculation != null) {
						return false;
					}
				} else if (!this$immatriculation.equals(other$immatriculation)) {
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
		return other instanceof Vehicule;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $id = this.getId();
		result = result * 59 + ($id == null ? 43 : $id.hashCode());
		Object $anneeFabrication = this.getAnneeFabrication();
		result = result * 59 + ($anneeFabrication == null ? 43 : $anneeFabrication.hashCode());
		Object $capacite = this.getCapacite();
		result = result * 59 + ($capacite == null ? 43 : $capacite.hashCode());
		Object $marque = this.getMarque();
		result = result * 59 + ($marque == null ? 43 : $marque.hashCode());
		Object $modele = this.getModele();
		result = result * 59 + ($modele == null ? 43 : $modele.hashCode());
		Object $immatriculation = this.getImmatriculation();
		result = result * 59 + ($immatriculation == null ? 43 : $immatriculation.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		Object $agence = this.getAgence();
		result = result * 59 + ($agence == null ? 43 : $agence.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "Vehicule(id=" + this.getId() + ", marque=" + this.getMarque() + ", modele=" + this.getModele() + ", anneeFabrication=" + this.getAnneeFabrication() + ", capacite=" + this.getCapacite() + ", immatriculation=" + this.getImmatriculation() + ", statut=" + this.getStatut() + ", agence=" + this.getAgence() + ")";
	}
}
