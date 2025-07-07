package PFE.CDSIR_AGENCY.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import java.util.Objects; // Importez Objects pour les méthodes equals et hashCode
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

	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore
	private List<Administrateur> administrateurs;

	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore
	private List<Vehicule> vehicules;

	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore
	private List<Voyage> voyages;

	@OneToMany(
			mappedBy = "agence",
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@JsonIgnore
	private List<Colis> colis;

	@Generated
	public Agence() {
	}

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

	// Réécriture des méthodes equals et hashCode pour utiliser java.util.Objects
	@Override
	@Generated
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Agence agence = (Agence) o;
		return Objects.equals(id, agence.id) &&
			   Objects.equals(nomAgence, agence.nomAgence) &&
			   Objects.equals(villeAgence, agence.villeAgence) &&
			   Objects.equals(localisation, agence.localisation) &&
			   Objects.equals(telephoneAgence, agence.telephoneAgence) &&
			   Objects.equals(statut, agence.statut);
	}

	@Override
	@Generated
	public int hashCode() {
		return Objects.hash(id, nomAgence, villeAgence, localisation, telephoneAgence, statut);
	}

	@Generated
	public String toString() {
		return "Agence(id=" + this.getId() + ", nomAgence=" + this.getNomAgence() + ", villeAgence=" + this.getVilleAgence() + ", localisation=" + this.getLocalisation() + ", telephoneAgence=" + this.getTelephoneAgence() + ", statut=" + this.getStatut() + ")";
	}
}
