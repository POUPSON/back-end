//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- N'oubliez pas cet import !
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Generated;

@Entity
@Table(
		name = "trajet",
		uniqueConstraints = {@UniqueConstraint(
				columnNames = {"ville_depart", "ville_destination", "quartier_depart", "quartier_destination"},
				name = "uk_trajet_unique"
		)}
)
public class Trajet {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	@Column(
			name = "id_trajet"
	)
	private Long id;
	@Column(
			name = "ville_depart",
			length = 100
	)
	private @NotBlank(
			message = "La ville de départ est requise"
	) @Size(
			max = 100,
			message = "La ville de départ ne doit pas dépasser 100 caractères"
	) String villeDepart;
	@Column(
			name = "ville_destination",
			length = 100
	)
	private @NotBlank(
			message = "La ville de destination est requise"
	) @Size(
			max = 100,
			message = "La ville de destination ne doit pas dépasser 100 caractères"
	) String villeDestination;
	@Column(
			name = "quartier_depart",
			length = 100
	)
	private @Size(
			max = 100,
			message = "Le quartier de départ ne doit pas dépasser 100 caractères"
	) String quartierDepart;
	@Column(
			name = "quartier_destination",
			length = 100
	)
	private @Size(
			max = 100,
			message = "Le quartier de destination ne doit pas dépasser 100 caractères"
	) String quartierDestination;
	@Column(
			name = "statut",
			length = 20
	)
	private @Size(
			max = 20,
			message = "Le statut ne doit pas dépasser 20 caractères"
	) String statut;

	@OneToMany(
			mappedBy = "trajet",
			cascade = {CascadeType.ALL},
			orphanRemoval = true
	)
	@JsonIgnore // <-- AJOUTEZ CETTE LIGNE ICI
	private List<Voyage> voyages;

	@Generated
	public Long getId() {
		return this.id;
	}

	@Generated
	public String getVilleDepart() {
		return this.villeDepart;
	}

	@Generated
	public String getVilleDestination() {
		return this.villeDestination;
	}

	@Generated
	public String getQuartierDepart() {
		return this.quartierDepart;
	}

	@Generated
	public String getQuartierDestination() {
		return this.quartierDestination;
	}

	@Generated
	public String getStatut() {
		return this.statut;
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
	public void setVilleDepart(final String villeDepart) {
		this.villeDepart = villeDepart;
	}

	@Generated
	public void setVilleDestination(final String villeDestination) {
		this.villeDestination = villeDestination;
	}

	@Generated
	public void setQuartierDepart(final String quartierDepart) {
		this.quartierDepart = quartierDepart;
	}

	@Generated
	public void setQuartierDestination(final String quartierDestination) {
		this.quartierDestination = quartierDestination;
	}

	@Generated
	public void setStatut(final String statut) {
		this.statut = statut;
	}

	@Generated
	public void setVoyages(final List<Voyage> voyages) {
		this.voyages = voyages;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Trajet)) {
			return false;
		} else {
			Trajet other = (Trajet)o;
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

				Object this$villeDepart = this.getVilleDepart();
				Object other$villeDepart = other.getVilleDepart();
				if (this$villeDepart == null) {
					if (other$villeDepart != null) {
						return false;
					}
				} else if (!this$villeDepart.equals(other$villeDepart)) {
					return false;
				}

				Object this$villeDestination = this.getVilleDestination();
				Object other$villeDestination = other.getVilleDestination();
				if (this$villeDestination == null) {
					if (other$villeDestination != null) {
						return false;
					}
				} else if (!this$villeDestination.equals(other$villeDestination)) {
					return false;
				}

				Object this$quartierDepart = this.getQuartierDepart();
				Object other$quartierDepart = other.getQuartierDepart();
				if (this$quartierDepart == null) {
					if (other$quartierDepart != null) {
						return false;
					}
				} else if (!this$quartierDepart.equals(other$quartierDepart)) {
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
		return other instanceof Trajet;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $id = this.getId();
		result = result * 59 + ($id == null ? 43 : $id.hashCode());
		Object $villeDepart = this.getVilleDepart();
		result = result * 59 + ($villeDepart == null ? 43 : $villeDepart.hashCode());
		Object $villeDestination = this.getVilleDestination();
		result = result * 59 + ($villeDestination == null ? 43 : $villeDestination.hashCode());
		Object $quartierDepart = this.getQuartierDepart();
		result = result * 59 + ($quartierDepart == null ? 43 : $quartierDepart.hashCode());
		Object $quartierDestination = this.getQuartierDestination();
		result = result * 59 + ($quartierDestination == null ? 43 : $quartierDestination.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "Trajet(id=" + this.getId() + ", villeDepart=" + this.getVilleDepart() + ", villeDestination=" + this.getVilleDestination() + ", quartierDepart=" + this.getQuartierDepart() + ", quartierDestination=" + this.getQuartierDestination() + ", statut=" + this.getStatut() + ")";
	}
}
