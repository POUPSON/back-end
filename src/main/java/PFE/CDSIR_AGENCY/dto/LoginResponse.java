//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

import lombok.Generated;

public class LoginResponse {
	private boolean success;
	private String message;
	private String token;
	private Long userId;
	private String nom;
	private String prenom;
	private String email;
	private String role;

	public LoginResponse() {
	}

	public LoginResponse(boolean success, String message, String token, Long userId) {
		this.success = success;
		this.message = message;
		this.token = token;
		this.userId = userId;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Generated
	public String getNom() {
		return this.nom;
	}

	@Generated
	public String getPrenom() {
		return this.prenom;
	}

	@Generated
	public String getEmail() {
		return this.email;
	}

	@Generated
	public String getRole() {
		return this.role;
	}

	@Generated
	public void setNom(final String nom) {
		this.nom = nom;
	}

	@Generated
	public void setPrenom(final String prenom) {
		this.prenom = prenom;
	}

	@Generated
	public void setEmail(final String email) {
		this.email = email;
	}

	@Generated
	public void setRole(final String role) {
		this.role = role;
	}

	@Generated
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof LoginResponse)) {
			return false;
		} else {
			LoginResponse other = (LoginResponse)o;
			if (!other.canEqual(this)) {
				return false;
			} else if (this.isSuccess() != other.isSuccess()) {
				return false;
			} else {
				Object this$userId = this.getUserId();
				Object other$userId = other.getUserId();
				if (this$userId == null) {
					if (other$userId != null) {
						return false;
					}
				} else if (!this$userId.equals(other$userId)) {
					return false;
				}

				Object this$message = this.getMessage();
				Object other$message = other.getMessage();
				if (this$message == null) {
					if (other$message != null) {
						return false;
					}
				} else if (!this$message.equals(other$message)) {
					return false;
				}

				Object this$token = this.getToken();
				Object other$token = other.getToken();
				if (this$token == null) {
					if (other$token != null) {
						return false;
					}
				} else if (!this$token.equals(other$token)) {
					return false;
				}

				Object this$nom = this.getNom();
				Object other$nom = other.getNom();
				if (this$nom == null) {
					if (other$nom != null) {
						return false;
					}
				} else if (!this$nom.equals(other$nom)) {
					return false;
				}

				Object this$prenom = this.getPrenom();
				Object other$prenom = other.getPrenom();
				if (this$prenom == null) {
					if (other$prenom != null) {
						return false;
					}
				} else if (!this$prenom.equals(other$prenom)) {
					return false;
				}

				Object this$email = this.getEmail();
				Object other$email = other.getEmail();
				if (this$email == null) {
					if (other$email != null) {
						return false;
					}
				} else if (!this$email.equals(other$email)) {
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

				return true;
			}
		}
	}

	@Generated
	protected boolean canEqual(final Object other) {
		return other instanceof LoginResponse;
	}

	@Generated
	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		result = result * 59 + (this.isSuccess() ? 79 : 97);
		Object $userId = this.getUserId();
		result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
		Object $message = this.getMessage();
		result = result * 59 + ($message == null ? 43 : $message.hashCode());
		Object $token = this.getToken();
		result = result * 59 + ($token == null ? 43 : $token.hashCode());
		Object $nom = this.getNom();
		result = result * 59 + ($nom == null ? 43 : $nom.hashCode());
		Object $prenom = this.getPrenom();
		result = result * 59 + ($prenom == null ? 43 : $prenom.hashCode());
		Object $email = this.getEmail();
		result = result * 59 + ($email == null ? 43 : $email.hashCode());
		Object $role = this.getRole();
		result = result * 59 + ($role == null ? 43 : $role.hashCode());
		return result;
	}

	@Generated
	public String toString() {
		return "LoginResponse(success=" + this.isSuccess() + ", message=" + this.getMessage() + ", token=" + this.getToken() + ", userId=" + this.getUserId() + ", nom=" + this.getNom() + ", prenom=" + this.getPrenom() + ", email=" + this.getEmail() + ", role=" + this.getRole() + ")";
	}

	@Generated
	public LoginResponse(final boolean success, final String message, final String token, final Long userId, final String nom, final String prenom, final String email, final String role) {
		this.success = success;
		this.message = message;
		this.token = token;
		this.userId = userId;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.role = role;
	}
}
