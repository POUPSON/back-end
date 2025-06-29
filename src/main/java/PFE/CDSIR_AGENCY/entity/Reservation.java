//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Generated;

@Entity
@Table(
		name = "reservation"
)
public class Reservation {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	@Column(
			name = "id_reservation"
	)
	private Long id;
	@ManyToOne(
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "id_client"
	)
	private Client client;
	@Column(
			name = "client_nom_invite"
	)
	private String clientNom;
	@Column(
			name = "client_prenom_invite"
	)
	private String clientPrenom;
	@Column(
			name = "client_email_invite"
	)
	private String clientEmail;
	@Column(
			name = "client_telephone_invite"
	)
	private String clientTelephone;
	@ManyToOne(
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "id_voyage",
			nullable = false
	)
	private @NotNull(
			message = "Le voyage est requis"
	) Voyage voyage;
	@Column(
			name = "date_reservation",
			nullable = false
	)
	private LocalDateTime reservationDate;
	@Column(
			name = "siege_reserver"
	)
	private @NotBlank(
			message = "Le siège réservé est requis"
	) String siegeReserver;
	@Column(
			name = "classe"
	)
	private @NotBlank(
			message = "La classe est requise"
	) String classe;
	@Column(
			name = "mode_paiement"
	)
	private @NotBlank(
			message = "Le mode de paiement est requis"
	) String modePaiement;
	@Column(
			name = "reference_paiement"
	)
	private String paymentReference;
	@Column(
			name = "code_validation",
			unique = true
	)
	private String codeValidation;
	@Column(
			name = "montant",
			nullable = false
	)
	private @NotNull(
			message = "Le montant est requis"
	) @DecimalMin(
			value = "0.01",
			message = "Le montant doit être supérieur à zéro"
	) Double montant;
	@Column(
			name = "statut",
			nullable = false
	)
	private @NotBlank(
			message = "Le statut est requis"
	) String statut;

	public String getCodeValidation() {
		return this.codeValidation;
	}

	public void setCodeValidation(String codeValidation) {
		this.codeValidation = codeValidation;
	}

	public String getPaymentReference() {
		return this.paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getClientNom() {
		return this.clientNom;
	}

	public void setClientNom(String clientNom) {
		this.clientNom = clientNom;
	}

	public String getClientPrenom() {
		return this.clientPrenom;
	}

	public void setClientPrenom(String clientPrenom) {
		this.clientPrenom = clientPrenom;
	}

	public String getClientEmail() {
		return this.clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getClientTelephone() {
		return this.clientTelephone;
	}

	public void setClientTelephone(String clientTelephone) {
		this.clientTelephone = clientTelephone;
	}

	@Generated
	public Long getId() {
		return this.id;
	}

	@Generated
	public Client getClient() {
		return this.client;
	}

	@Generated
	public Voyage getVoyage() {
		return this.voyage;
	}

	@Generated
	public LocalDateTime getReservationDate() {
		return this.reservationDate;
	}

	@Generated
	public String getSiegeReserver() {
		return this.siegeReserver;
	}

	@Generated
	public String getClasse() {
		return this.classe;
	}

	@Generated
	public String getModePaiement() {
		return this.modePaiement;
	}

	@Generated
	public Double getMontant() {
		return this.montant;
	}

	@Generated
	public String getStatut() {
		return this.statut;
	}

	@Generated
	public void setId(final Long id) {
		this.id = id;
	}

	@Generated
	public void setClient(final Client client) {
		this.client = client;
	}

	@Generated
	public void setVoyage(final Voyage voyage) {
		this.voyage = voyage;
	}

	@Generated
	public void setReservationDate(final LocalDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}

	@Generated
	public void setSiegeReserver(final String siegeReserver) {
		this.siegeReserver = siegeReserver;
	}

	@Generated
	public void setClasse(final String classe) {
		this.classe = classe;
	}

	@Generated
	public void setModePaiement(final String modePaiement) {
		this.modePaiement = modePaiement;
	}

	@Generated
	public void setMontant(final Double montant) {
		this.montant = montant;
	}

	@Generated
	public void setStatut(final String statut) {
		this.statut = statut;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Reservation)) {
			return false;
		} else {
			Reservation other = (Reservation)o;
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

				Object this$montant = this.getMontant();
				Object other$montant = other.getMontant();
				if (this$montant == null) {
					if (other$montant != null) {
						return false;
					}
				} else if (!this$montant.equals(other$montant)) {
					return false;
				}

				Object this$client = this.getClient();
				Object other$client = other.getClient();
				if (this$client == null) {
					if (other$client != null) {
						return false;
					}
				} else if (!this$client.equals(other$client)) {
					return false;
				}

				Object this$clientNom = this.getClientNom();
				Object other$clientNom = other.getClientNom();
				if (this$clientNom == null) {
					if (other$clientNom != null) {
						return false;
					}
				} else if (!this$clientNom.equals(other$clientNom)) {
					return false;
				}

				Object this$clientPrenom = this.getClientPrenom();
				Object other$clientPrenom = other.getClientPrenom();
				if (this$clientPrenom == null) {
					if (other$clientPrenom != null) {
						return false;
					}
				} else if (!this$clientPrenom.equals(other$clientPrenom)) {
					return false;
				}

				Object this$clientEmail = this.getClientEmail();
				Object other$clientEmail = other.getClientEmail();
				if (this$clientEmail == null) {
					if (other$clientEmail != null) {
						return false;
					}
				} else if (!this$clientEmail.equals(other$clientEmail)) {
					return false;
				}

				Object this$clientTelephone = this.getClientTelephone();
				Object other$clientTelephone = other.getClientTelephone();
				if (this$clientTelephone == null) {
					if (other$clientTelephone != null) {
						return false;
					}
				} else if (!this$clientTelephone.equals(other$clientTelephone)) {
					return false;
				}

				Object this$voyage = this.getVoyage();
				Object other$voyage = other.getVoyage();
				if (this$voyage == null) {
					if (other$voyage != null) {
						return false;
					}
				} else if (!this$voyage.equals(other$voyage)) {
					return false;
				}

				Object this$reservationDate = this.getReservationDate();
				Object other$reservationDate = other.getReservationDate();
				if (this$reservationDate == null) {
					if (other$reservationDate != null) {
						return false;
					}
				} else if (!this$reservationDate.equals(other$reservationDate)) {
					return false;
				}

				Object this$siegeReserver = this.getSiegeReserver();
				Object other$siegeReserver = other.getSiegeReserver();
				if (this$siegeReserver == null) {
					if (other$siegeReserver != null) {
						return false;
					}
				} else if (!this$siegeReserver.equals(other$siegeReserver)) {
					return false;
				}

				Object this$classe = this.getClasse();
				Object other$classe = other.getClasse();
				if (this$classe == null) {
					if (other$classe != null) {
						return false;
					}
				} else if (!this$classe.equals(other$classe)) {
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

				Object this$codeValidation = this.getCodeValidation();
				Object other$codeValidation = other.getCodeValidation();
				if (this$codeValidation == null) {
					if (other$codeValidation != null) {
						return false;
					}
				} else if (!this$codeValidation.equals(other$codeValidation)) {
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
		return other instanceof Reservation;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $id = this.getId();
		result = result * 59 + ($id == null ? 43 : $id.hashCode());
		Object $montant = this.getMontant();
		result = result * 59 + ($montant == null ? 43 : $montant.hashCode());
		Object $client = this.getClient();
		result = result * 59 + ($client == null ? 43 : $client.hashCode());
		Object $clientNom = this.getClientNom();
		result = result * 59 + ($clientNom == null ? 43 : $clientNom.hashCode());
		Object $clientPrenom = this.getClientPrenom();
		result = result * 59 + ($clientPrenom == null ? 43 : $clientPrenom.hashCode());
		Object $clientEmail = this.getClientEmail();
		result = result * 59 + ($clientEmail == null ? 43 : $clientEmail.hashCode());
		Object $clientTelephone = this.getClientTelephone();
		result = result * 59 + ($clientTelephone == null ? 43 : $clientTelephone.hashCode());
		Object $voyage = this.getVoyage();
		result = result * 59 + ($voyage == null ? 43 : $voyage.hashCode());
		Object $reservationDate = this.getReservationDate();
		result = result * 59 + ($reservationDate == null ? 43 : $reservationDate.hashCode());
		Object $siegeReserver = this.getSiegeReserver();
		result = result * 59 + ($siegeReserver == null ? 43 : $siegeReserver.hashCode());
		Object $classe = this.getClasse();
		result = result * 59 + ($classe == null ? 43 : $classe.hashCode());
		Object $modePaiement = this.getModePaiement();
		result = result * 59 + ($modePaiement == null ? 43 : $modePaiement.hashCode());
		Object $paymentReference = this.getPaymentReference();
		result = result * 59 + ($paymentReference == null ? 43 : $paymentReference.hashCode());
		Object $codeValidation = this.getCodeValidation();
		result = result * 59 + ($codeValidation == null ? 43 : $codeValidation.hashCode());
		Object $statut = this.getStatut();
		result = result * 59 + ($statut == null ? 43 : $statut.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "Reservation(id=" + this.getId() + ", client=" + this.getClient() + ", clientNom=" + this.getClientNom() + ", clientPrenom=" + this.getClientPrenom() + ", clientEmail=" + this.getClientEmail() + ", clientTelephone=" + this.getClientTelephone() + ", voyage=" + this.getVoyage() + ", reservationDate=" + this.getReservationDate() + ", siegeReserver=" + this.getSiegeReserver() + ", classe=" + this.getClasse() + ", modePaiement=" + this.getModePaiement() + ", paymentReference=" + this.getPaymentReference() + ", codeValidation=" + this.getCodeValidation() + ", montant=" + this.getMontant() + ", statut=" + this.getStatut() + ")";
	}
}
