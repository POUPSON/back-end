package PFE.CDSIR_AGENCY.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // Importez cette annotation
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType; // Ajoutez FetchType si non présent
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Generated;

@Entity
@Table(
		name = "agence",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"nom_agence"}, name = "uk_agence_nom_agence"),
				@UniqueConstraint(columnNames = {"telephone_agence"}, name = "uk_agence_telephone_agence")
		}
)
public class Agence {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	@Column(
			name = "id_agence"
	)
	private Long id;

	@Column(
			name = "nom_agence",
			unique = true,
			length = 100
	)
	private @NotBlank(
			message = "Le nom de l'agence est requis"
	) @Size(
			max = 100,
			message = "Le nom de l'agence ne doit pas dépasser 100 caractères"
	) String nomAgence;

	@Column(
			name = "ville_agence",
			length = 100
	)
	private @NotBlank(
			message = "La ville de l'agence est requise"
	) @Size(
			max = 100,
			message = "La ville ne doit pas dépasser 100 caractères"
	) String villeAgence;

	@Column(
			name = "localisation",
			length = 150
	)
	private @Size(
			max = 150,
			message = "La localisation ne doit pas dépasser 150 caractères"
	) String localisation;

	@Column(
			name = "telephone_agence",
			unique = true,
			length = 20
	)
	private @NotBlank(
			message = "Le téléphone de l'agence est requis"
	) @Size(
			max = 20,
			message = "Le numéro de téléphone ne doit pas dépasser 20 caractères"
	)
	@Pattern(regexp = "^[0-9]{8,15}$", message = "Le format du téléphone est invalide (8 à 15 chiffres)")
	String telephoneAgence;

	@Column(
			name = "statut",
			length = 20
	)
	private String statut;

	// AJOUTEZ @JsonIgnore sur toutes les collections OneToMany
	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore // <-- AJOUTÉ
	private List<Administrateur> administrateurs;

	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore // <-- AJOUTÉ
	private List<Vehicule> vehicules;

	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore // <-- AJOUTÉ
	private List<Voyage> voyages;

	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore // <-- AJOUTÉ
	private List<Colis> colis;

	// --- Getters et Setters générés par Lombok ou manuellement ---
	// (Les annotations @Generated indiquent qu'ils sont générés)

	@Generated
	public Long getId() {
		return this.id;
	}

	@Generated
	public String getNomAgence() {
		return this.nomAgence;
	}

	@Generated
	public String getVilleAgence() {
		return this.villeAgence;
	}

	@Generated
	public String getLocalisation() {
		return this.localisation;
	}

	@Generated
	public String getTelephoneAgence() {
		return this.telephoneAgence;
	}

	@Generated
	public String getStatut() {
		return this.statut;
	}

	@Generated
	public List<Administrateur> getAdministrateurs() {
		return this.administrateurs;
	}

	@Generated
	public List<Vehicule> getVehicules() {
		return this.vehicules;
	}

	@Generated
	public List<Voyage> getVoyages() {
		return this.voyages;
	}

	@Generated
	public List<Colis> getColis() {
		return this.colis;
	}

	@Generated
	public void setId(final Long id) {
		this.id = id;
	}

	@Generated
	public void setNomAgence(final String nomAgence) {
		this.nomAgence = nomAgence;
	}

	@Generated
	public void setVilleAgence(final String villeAgence) {
		this.villeAgence = villeAgence;
	}

	@Generated
	public void setLocalisation(final String localisation) {
		this.localisation = localisation;
	}

	@Generated
	public void setTelephoneAgence(final String telephoneAgence) {
		this.telephoneAgence = telephoneAgence;
	}

	@Generated
	public void setStatut(final String statut) {
		this.statut = statut;
	}

	@Generated
	public void setAdministrateurs(final List<Administrateur> administrateurs) {
		this.administrateurs = administrateurs;
	}

	@Generated
	public void setVehicules(final List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}

	@Generated
	public void setVoyages(final List<Voyage> voyages) {
		this.voyages = voyages;
	}

	@Generated
	public void setColis(final List<Colis> colis) {
		this.colis = colis;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Agence)) {
			return false;
		} else {
			Agence other = (Agence)o;
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

				Object this$nomAgence = this.getNomAgence();
				Object other$nomAgence = other.getNomAgence();
				if (this$nomAgence == null) {
					if (other$nomAgence != null) {
						return false;
					}
				} else if (!this$nomAgence.equals(other$nomAgence)) {
					return false;
				}

				Object this$villeAgence = this.getVilleAgence();
				Object other$villeAgence = other.getVilleAgence();
				if (this$villeAgence == null) {
					if (other$villeAgence != null) {
						return false;
					}
				} else if (!this$villeAgence.equals(other$villeAgence)) {
					return false;
				}

				Object this$localisation = this.getLocalisation();
				Object other$localisation = other.getLocalisation();
				if (this$localisation == null) {
					if (other$localisation != null) {
						return false;
					}
				} else if (!this$localisation.equals(other$localisation)) {
					return false;
				}

				Object this$telephoneAgence = this.getTelephoneAgence();
				Object other$telephoneAgence = other.getTelephoneAgence();
				if (this$telephoneAgence == null) {
					if (other$telephoneAgence != null) {
						return false;
					}
				} else if (!this$telephoneAgence.equals(other$telephoneAgence)) {
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
		return other instanceof Agence;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $id = this.getId();
		result = result * 59 + ($id == null ? 43 : $id.hashCode());
		Object $nomAgence = this.getNomAgence();
		result = result * 59 + ($nomAgence == null ? 43 : $nomAgence.hashCode());
		Object $villeAgence = this.getVilleAgence();
		result = result * 59 + ($villeAgence == null ? 43 : $villeAgence.hashCode());
		Object $localisation = this.getLocalisation();
		result = result * 59 + ($localisation == null ? 43 : $localisation.hashCode());
		Object $telephoneAgence = this.getTelephoneAgence();
		result = result * 59 + ($telephoneAgence == null ? 43 : $telephoneAgence.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "Agence(id=" + this.getId() + ", nomAgence=" + this.getNomAgence() + ", villeAgence=" + this.getVilleAgence() + ", localisation=" + this.getLocalisation() + ", telephoneAgence=" + this.getTelephoneAgence() + ", statut=" + this.getStatut() + ")";
	}
}
