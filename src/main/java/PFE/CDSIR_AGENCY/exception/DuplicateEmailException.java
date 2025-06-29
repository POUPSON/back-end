package PFE.CDSIR_AGENCY.exception;

public class DuplicateEmailException extends RuntimeException {
	public DuplicateEmailException(String message) {
		super(message);
	}
}