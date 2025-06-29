package PFE.CDSIR_AGENCY.exception;

// Hérite de RuntimeException ou de ReservationException si vous l'avez définie
public class SeatAlreadyReservedException extends RuntimeException { // Ou extends ReservationException
	public SeatAlreadyReservedException(String siege) {
		super("Le siège " + siege + " est déjà réservé.");
	}
}