//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Generated;

public class LoginRequest {
	private @Email(
			message = "L'email doit être valide"
	) @NotBlank(
			message = "L'email ne peut pas être vide"
	) String email;
	private @NotBlank(
			message = "Le mot de passe ne peut pas être vide"
	) String motPasse;

	@Generated
	public String getEmail() {
		return this.email;
	}

	@Generated
	public String getMotPasse() {
		return this.motPasse;
	}

	@Generated
	public void setEmail(final String email) {
		this.email = email;
	}

	@Generated
	public void setMotPasse(final String motPasse) {
		this.motPasse = motPasse;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof LoginRequest)) {
			return false;
		} else {
			LoginRequest other = (LoginRequest)o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				Object this$email = this.getEmail();
				Object other$email = other.getEmail();
				if (this$email == null) {
					if (other$email != null) {
						return false;
					}
				} else if (!this$email.equals(other$email)) {
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

				return true;
			}
		}
	}

	@Generated
	protected boolean canEqual(final Object other) {
		return other instanceof LoginRequest;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $email = this.getEmail();
		result = result * 59 + ($email == null ? 43 : $email.hashCode());
		Object $motPasse = this.getMotPasse();
		result = result * 59 + ($motPasse == null ? 43 : $motPasse.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "LoginRequest(email=" + this.getEmail() + ", motPasse=" + this.getMotPasse() + ")";
	}
}
