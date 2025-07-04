package PFE.CDSIR_AGENCY.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import lombok.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
        name = "administrateur",
        uniqueConstraints = {
            @UniqueConstraint(
                columnNames = {"numero_cni"},
                name = "uk_administrateur_numero_cni"
            ),
            // AJOUTEZ UNE CONTRAINTE D'UNICITÉ POUR L'EMAIL
            @UniqueConstraint(
                columnNames = {"email_administrateur"}, // Nom de la colonne dans la DB
                name = "uk_administrateur_email"
            )
        }
)
public class Administrateur implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id_administrateur"
    )
    private Long id;

    @Column(
            name = "nom_administrateur"
    )
    private @NotBlank(
            message = "Le nom de l'administrateur est requis"
    ) @Size(
            max = 100,
            message = "Le nom ne doit pas dépasser 100 caractères"
    ) String nomAdministrateur;

    // NOUVEAU CHAMP EMAIL
    @Column(
            name = "email_administrateur", // Nom de la colonne dans la base de données
            nullable = false,
            unique = true // L'email doit être unique
    )
    private @NotBlank(
            message = "L'email de l'administrateur est requis"
    ) @Size(
            max = 255,
            message = "L'email ne doit pas dépasser 255 caractères"
    ) String email; // <-- AJOUTEZ CE CHAMP

    @Column(
            name = "numero_cni",
            unique = true // Assurez-vous que c'est bien unique ici aussi
    )
    private @NotBlank(
            message = "Le numéro de CNI est requis"
    ) @Size(
            max = 20,
            message = "Le numéro de CNI ne doit pas dépasser 20 caractères"
    ) String numeroCni;

    @Column(
            name = "mot_passe"
    )
    private @NotBlank(
            message = "Le mot de passe est requis"
    ) @Size(
            max = 255,
            message = "Le mot de passe est trop long"
    ) String motPasse;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "id_agence"
    )
    private Agence agence;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "role",
            nullable = false
    )
    private Role role;

    @Column(
            name = "statut"
    )
    private String statut; // Correspond à 'enabled' et 'accountLocked' de l'autre version

    @Column(
            name = "date_creation",
            updatable = false
    )
    private LocalDateTime dateCreation;

    @Column(
            name = "date_modification"
    )
    private LocalDateTime dateModification;

    @PreUpdate
    protected void onUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    public String getPassword() {
        return this.motPasse;
    }

    @Override
    public String getUsername() {
        return this.email; // <-- CHANGEMENT ICI : UTILISE L'EMAIL COMME USERNAME
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        // Si 'statut' est utilisé pour le verrouillage, ajustez ici.
        // Par exemple, si "ACTIF" signifie non verrouillé.
        return "ACTIF".equalsIgnoreCase(this.statut);
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return "ACTIF".equalsIgnoreCase(this.statut); // Si "ACTIF" signifie activé
    }

    @Generated
    public Administrateur() {
        this.role = Role.ADMIN;
        this.statut = "ACTIF";
        this.dateCreation = LocalDateTime.now();
    }

    // NOUVEAUX GETTER ET SETTER POUR EMAIL
    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public void setEmail(final String email) {
        this.email = email;
    }

    // --- Méthodes existantes (gardées pour éviter de casser le code) ---
    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getNomAdministrateur() {
        return this.nomAdministrateur;
    }

    @Generated
    public String getNumeroCni() {
        return this.numeroCni;
    }

    @Generated
    public String getMotPasse() {
        return this.motPasse;
    }

    @Generated
    public Agence getAgence() {
        return this.agence;
    }

    @Generated
    public Role getRole() {
        return this.role;
    }

    @Generated
    public String getStatut() {
        return this.statut;
    }

    @Generated
    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    @Generated
    public LocalDateTime getDateModification() {
        return this.dateModification;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setNomAdministrateur(final String nomAdministrateur) {
        this.nomAdministrateur = nomAdministrateur;
    }

    @Generated
    public void setNumeroCni(final String numeroCni) {
        this.numeroCni = numeroCni;
    }

    @Generated
    public void setMotPasse(final String motPasse) {
        this.motPasse = motPasse;
    }

    @Generated
    public void setAgence(final Agence agence) {
        this.agence = agence;
    }

    @Generated
    public void setRole(final Role role) {
        this.role = role;
    }

    @Generated
    public void setStatut(final String statut) {
        this.statut = statut;
    }

    @Generated
    public void setDateCreation(final LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Generated
    public void setDateModification(final LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Administrateur)) {
            return false;
        } else {
            Administrateur other = (Administrateur)o;
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

                Object this$nomAdministrateur = this.getNomAdministrateur();
                Object other$nomAdministrateur = other.getNomAdministrateur();
                if (this$nomAdministrateur == null) {
                    if (other$nomAdministrateur != null) {
                        return false;
                    }
                } else if (!this$nomAdministrateur.equals(other$nomAdministrateur)) {
                    return false;
                }

                // AJOUT DE LA COMPARAISON POUR L'EMAIL
                Object this$email = this.getEmail();
                Object other$email = other.getEmail();
                if (this$email == null) {
                    if (other$email != null) {
                        return false;
                    }
                } else if (!this$email.equals(other$email)) {
                    return false;
                }

                Object this$numeroCni = this.getNumeroCni();
                Object other$numeroCni = other.getNumeroCni();
                if (this$numeroCni == null) {
                    if (other$numeroCni != null) {
                        return false;
                    }
                } else if (!this$numeroCni.equals(other$numeroCni)) {
                    return false;
                }

                Object this$motPasse = this.getMotPasse();
                Object other$motPasse = other.getMotPasse();
                if (this$motPasse == null) {
                    if (other$motPasse != null) {
                        return false;
                    }
                } else if (!this$motPasse.equals(other$motPasse)) {
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

                Object this$role = this.getRole();
                Object other$role = other.getRole();
                if (this$role == null) {
                    if (other$role != null) {
                        return false;
                    }
                } else if (!this$role.equals(other$role)) {
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

                Object this$dateCreation = this.getDateCreation();
                Object other$dateCreation = other.getDateCreation();
                if (this$dateCreation == null) {
                    if (other$dateCreation != null) {
                        return false;
                    }
                } else if (!this$dateCreation.equals(other$dateCreation)) {
                    return false;
                }

                Object this$dateModification = this.getDateModification();
                Object other$dateModification = other.getDateModification();
                if (this$dateModification == null) {
                    if (other$dateModification != null) {
                        return false;
                    }
                } else if (!this$dateModification.equals(other$dateModification)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof Administrateur;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $nomAdministrateur = this.getNomAdministrateur();
        result = result * 59 + ($nomAdministrateur == null ? 43 : $nomAdministrateur.hashCode());
        Object $email = this.getEmail(); // AJOUT DE L'EMAIL AU HASHCODE
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        Object $numeroCni = this.getNumeroCni();
        result = result * 59 + ($numeroCni == null ? 43 : $numeroCni.hashCode());
        Object $motPasse = this.getMotPasse();
        result = result * 59 + ($motPasse == null ? 43 : $motPasse.hashCode());
        Object $agence = this.getAgence();
        result = result * 59 + ($agence == null ? 43 : $agence.hashCode());
        Object $role = this.getRole();
        result = result * 59 + ($role == null ? 43 : $role.hashCode());
        Object $statut = this.getStatut();
        result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
        Object $dateCreation = this.getDateCreation();
        result = result * 59 + ($dateCreation == null ? 43 : $dateCreation.hashCode());
        Object $dateModification = this.getDateModification();
        result = result * 59 + ($dateModification == null ? 43 : $dateModification.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "Administrateur(id=" + this.getId() + ", nomAdministrateur=" + this.getNomAdministrateur() + ", email=" + this.getEmail() + ", numeroCni=" + this.getNumeroCni() + ", motPasse=" + this.getMotPasse() + ", agence=" + this.getAgence() + ", role=" + this.getRole() + ", statut=" + this.getStatut() + ", dateCreation=" + this.getDateCreation() + ", dateModification=" + this.getDateModification() + ")";
    }
}
