package PFE.CDSIR_AGENCY.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import lombok.Generated; // Gardez cette annotation si vous utilisez Lombok et que vous voulez exclure ces méthodes de son traitement
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
		name = "client",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"email"}, name = "uk_client_email"),
				@UniqueConstraint(columnNames = {"telephone"}, name = "uk_client_telephone"),
				@UniqueConstraint(columnNames = {"numeroCni"}, name = "uk_client_numero_cni")
		}
)
public class Client implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client")
	private Long id;

	@NotBlank(message = "Le nom est requis")
	@Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
	private String nom;

	@NotBlank(message = "Le prénom est requis")
	@Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
	private String prenom;

	@Column(unique = true)
	@Email(message = "L'email doit être valide")
	@NotBlank(message = "L'email est requis")
	private String email;

	@Column(name = "mot_passe")
	@NotBlank(message = "Le mot de passe est requis")
	@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
	private String motPasse;

	@Column(unique = true)
	@NotBlank(message = "Le numéro de téléphone est requis")
	@Pattern(regexp = "^(\\+\\d{1,3})?\\d{10}$", message = "Le numéro de téléphone n'est pas valide")
	private String telephone;

	@Column(name = "numero_cni", unique = true)
	@NotBlank(message = "Le numéro de CNI est requis")
	private String numeroCni;

	@Column(name = "adresse")
	@Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
	private String adresse;

	@Column(name = "date_naissance")
	@NotNull(message = "La date de naissance est requise") // Ajouté si vous voulez la rendre obligatoire
	private LocalDate dateNaissance;

	@Column(name = "lieu_residence")
	@Size(max = 100, message = "Le lieu de résidence ne doit pas dépasser 100 caractères")
	private String lieuResidence;

	@Column(name = "enabled")
	private Boolean enabled = true;

	@Column(name = "reset_token")
	private String resetToken;

	@Column(name = "token_expiration")
	private LocalDateTime tokenExpiration;

	// NE PAS INITIALISER ICI. Laissez @PrePersist le faire.
	@Column(name = "date_creation", updatable = false, nullable = false)
	private LocalDateTime dateCreation;

	// NE PAS INITIALISER ICI. Laissez @PrePersist/@PreUpdate le faire.
	@Column(name = "date_modification", nullable = false)
	private LocalDateTime dateModification;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false) // Le rôle est aussi généralement non-null
	private Role role;

	@Column(name = "account_locked")
	private boolean accountLocked = false;

	@Column(name = "failed_login_attempts")
	private int failedLoginAttempts = 0;

	@Column(name = "two_factor_enabled")
	private boolean twoFactorEnabled = false;

	@Column(name = "two_factor_secret", length = 100)
	private String twoFactorSecret;

	// ====================================================================
	// RÉINTRODUISEZ CES MÉTHODES POUR GÉRER AUTOMATIQUEMENT LES DATES
	// ====================================================================
	@PrePersist
	protected void onCreate() {
		this.dateCreation = LocalDateTime.now();
		this.dateModification = LocalDateTime.now(); // Initialiser aussi dateModification à la création
	}

	@PreUpdate
	protected void onUpdate() {
		this.dateModification = LocalDateTime.now();
	}
	// ====================================================================

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
	}

	@Override
	public String getPassword() {
		return this.motPasse;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled != null && this.enabled;
	}

	@Generated
	public Client() {
		this.role = Role.CLIENT;
	}

	// Getters and Setters (laissez-les tels quels)
	@Generated
	public Long getId() {
		return id;
	}

	@Generated
	public void setId(Long id) {
		this.id = id;
	}

	@Generated
	public String getNom() {
		return nom;
	}

	@Generated
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Generated
	public String getPrenom() {
		return prenom;
	}

	@Generated
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Generated
	public String getEmail() {
		return email;
	}

	@Generated
	public void setEmail(String email) {
		this.email = email;
	}

	@Generated
	public String getMotPasse() {
		return motPasse;
	}

	@Generated
	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}

	@Generated
	public String getTelephone() {
		return telephone;
	}

	@Generated
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Generated
	public String getNumeroCni() {
		return numeroCni;
	}

	@Generated
	public void setNumeroCni(String numeroCni) {
		this.numeroCni = numeroCni;
	}

	@Generated
	public String getAdresse() {
		return adresse;
	}

	@Generated
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Generated
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	@Generated
	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	@Generated
	public String getLieuResidence() {
		return lieuResidence;
	}

	@Generated
	public void setLieuResidence(String lieuResidence) {
		this.lieuResidence = lieuResidence;
	}

	@Generated
	public Boolean getEnabled() {
		return enabled;
	}

	@Generated
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Generated
	public String getResetToken() {
		return resetToken;
	}

	@Generated
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	@Generated
	public LocalDateTime getTokenExpiration() {
		return tokenExpiration;
	}

	@Generated
	public void setTokenExpiration(LocalDateTime tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

	@Generated
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	@Generated
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Generated
	public LocalDateTime getDateModification() {
		return dateModification;
	}

	@Generated
	public void setDateModification(LocalDateTime dateModification) {
		this.dateModification = dateModification;
	}

	@Generated
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	@Generated
	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Generated
	public Role getRole() {
		return role;
	}

	@Generated
	public void setRole(Role role) {
		this.role = role;
	}

	@Generated
	public boolean isAccountLocked() {
		return accountLocked;
	}

	@Generated
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	@Generated
	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	@Generated
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	@Generated
	public boolean isTwoFactorEnabled() {
		return twoFactorEnabled;
	}

	@Generated
	public void setTwoFactorEnabled(boolean twoFactorEnabled) {
		this.twoFactorEnabled = twoFactorEnabled;
	}

	@Generated
	public String getTwoFactorSecret() {
		return twoFactorSecret;
	}

	@Generated
	public void setTwoFactorSecret(String twoFactorSecret) {
		this.twoFactorSecret = twoFactorSecret;
	}

	@Generated
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Client)) return false;
		Client client = (Client) o;
		return accountLocked == client.accountLocked &&
				failedLoginAttempts == client.failedLoginAttempts &&
				twoFactorEnabled == client.twoFactorEnabled &&
				Objects.equals(id, client.id) &&
				Objects.equals(nom, client.nom) &&
				Objects.equals(prenom, client.prenom) &&
				Objects.equals(email, client.email) &&
				Objects.equals(motPasse, client.motPasse) &&
				Objects.equals(telephone, client.telephone) &&
				Objects.equals(numeroCni, client.numeroCni) &&
				Objects.equals(adresse, client.adresse) &&
				Objects.equals(dateNaissance, client.dateNaissance) &&
				Objects.equals(lieuResidence, client.lieuResidence) &&
				Objects.equals(enabled, client.enabled) &&
				Objects.equals(resetToken, client.resetToken) &&
				Objects.equals(tokenExpiration, client.tokenExpiration) &&
				Objects.equals(dateCreation, client.dateCreation) &&
				Objects.equals(dateModification, client.dateModification) &&
				Objects.equals(lastLogin, client.lastLogin) &&
				Objects.equals(twoFactorSecret, client.twoFactorSecret) &&
				role == client.role;
	}

	@Generated
	@Override
	public int hashCode() {
		return Objects.hash(id, nom, prenom, email, motPasse, telephone, numeroCni,
				adresse, dateNaissance, lieuResidence, enabled, resetToken,
				tokenExpiration, dateCreation, dateModification, lastLogin, role,
				accountLocked, failedLoginAttempts, twoFactorEnabled, twoFactorSecret);
	}

	@Generated
	@Override
	public String toString() {
		return "Client{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", email='" + email + '\'' +
				", motPasse='" + motPasse + '\'' +
				", telephone='" + telephone + '\'' +
				", numeroCni='" + numeroCni + '\'' +
				", adresse='" + adresse + '\'' +
				", dateNaissance=" + dateNaissance +
				", lieuResidence='" + lieuResidence + '\'' +
				", enabled=" + enabled +
				", resetToken='" + resetToken + '\'' +
				", tokenExpiration=" + tokenExpiration +
				", dateCreation=" + dateCreation +
				", dateModification=" + dateModification +
				", lastLogin=" + lastLogin +
				", role=" + role +
				", accountLocked=" + accountLocked +
				", failedLoginAttempts=" + failedLoginAttempts +
				", twoFactorEnabled=" + twoFactorEnabled +
				", twoFactorSecret='" + twoFactorSecret + '\'' +
				'}';
	}

	public static enum Role {
		CLIENT,
		ADMIN,
		AGENT
	}
}
