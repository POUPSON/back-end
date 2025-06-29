// src/main/java/PFE/CDSIR_AGENCY/exception/NotFoundException.java
package PFE.CDSIR_AGENCY.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}