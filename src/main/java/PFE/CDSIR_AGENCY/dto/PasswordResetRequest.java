//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.dto;

public class PasswordResetRequest {
	private String email;
	private String newPassword;
	private String token;

	public PasswordResetRequest() {
	}

	public PasswordResetRequest(String email, String newPassword, String token) {
		this.email = email;
		this.newPassword = newPassword;
		this.token = token;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
