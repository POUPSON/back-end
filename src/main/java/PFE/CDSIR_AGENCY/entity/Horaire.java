//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;
import lombok.Generated;

@Entity
@Table(
		name = "horaire"
)
public class Horaire {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	@Column(
			name = "id_horaire"
	)
	private Long id;
	@Column(
			name = "heure_depart"
	)
	private @NotNull(
			message = "L'heure de départ est requise"
	) LocalTime heureDepart;
	@Column(
			name = "heure_arrive"
	)
	private @NotNull(
			message = "L'heure d'arrivée est requise"
	) LocalTime heureArrive;
	@Column(
			name = "duree"
	)
	private @NotNull(
			message = "La durée est requise"
	) LocalTime duree;
	@Column(
			name = "statut",
			length = 20
	)
	private @Size(
			max = 20,
			message = "Le statut ne doit pas dépasser 20 caractères"
	) String statut;
	@OneToMany(
			mappedBy = "horaire",
			cascade = {CascadeType.ALL},
			orphanRemoval = true
	)
	private List<Voyage> voyages;

	@Generated
	public Long getId() {
		return this.id;
	}

	@Generated
	public LocalTime getHeureDepart() {
		return this.heureDepart;
	}

	@Generated
	public LocalTime getHeureArrive() {
		return this.heureArrive;
	}

	@Generated
	public LocalTime getDuree() {
		return this.duree;
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
	public void setHeureDepart(final LocalTime heureDepart) {
		this.heureDepart = heureDepart;
	}

	@Generated
	public void setHeureArrive(final LocalTime heureArrive) {
		this.heureArrive = heureArrive;
	}

	@Generated
	public void setDuree(final LocalTime duree) {
		this.duree = duree;
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
		} else if (!(o instanceof Horaire)) {
			return false;
		} else {
			Horaire other = (Horaire)o;
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

				Object this$heureDepart = this.getHeureDepart();
				Object other$heureDepart = other.getHeureDepart();
				if (this$heureDepart == null) {
					if (other$heureDepart != null) {
						return false;
					}
				} else if (!this$heureDepart.equals(other$heureDepart)) {
					return false;
				}

				Object this$heureArrive = this.getHeureArrive();
				Object other$heureArrive = other.getHeureArrive();
				if (this$heureArrive == null) {
					if (other$heureArrive != null) {
						return false;
					}
				} else if (!this$heureArrive.equals(other$heureArrive)) {
					return false;
				}

				Object this$duree = this.getDuree();
				Object other$duree = other.getDuree();
				if (this$duree == null) {
					if (other$duree != null) {
						return false;
					}
				} else if (!this$duree.equals(other$duree)) {
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
		return other instanceof Horaire;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $id = this.getId();
		result = result * 59 + ($id == null ? 43 : $id.hashCode());
		Object $heureDepart = this.getHeureDepart();
		result = result * 59 + ($heureDepart == null ? 43 : $heureDepart.hashCode());
		Object $heureArrive = this.getHeureArrive();
		result = result * 59 + ($heureArrive == null ? 43 : $heureArrive.hashCode());
		Object $duree = this.getDuree();
		result = result * 59 + ($duree == null ? 43 : $duree.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "Horaire(id=" + this.getId() + ", heureDepart=" + this.getHeureDepart() + ", heureArrive=" + this.getHeureArrive() + ", duree=" + this.getDuree() + ", statut=" + this.getStatut() + ")";
	}
}
