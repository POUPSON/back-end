package PFE.CDSIR_AGENCY.service;

import PFE.CDSIR_AGENCY.dto.ReservationRequestDto;
import PFE.CDSIR_AGENCY.entity.Reservation;
import PFE.CDSIR_AGENCY.exception.ConflictException;
import PFE.CDSIR_AGENCY.exception.NotFoundException;
import PFE.CDSIR_AGENCY.exception.OperationNotAllowedException;
import java.util.List;

public interface ReservationService {
	Reservation createReservation(ReservationRequestDto reservationRequestDto);
	Reservation createReservationForClient(Long clientId, Long voyageId, ReservationRequestDto reservationRequestDto);
	Reservation getReservationById(Long id);
	Reservation getReservationByIdAndClientId(Long reservationId, Long clientId);
	List<Reservation> getAllReservations();
	List<Reservation> getReservationsByClientId(Long clientId);
	Reservation updateReservation(Long id, ReservationRequestDto reservationRequestDto);
	Reservation updateReservationForClient(Long reservationId, Long clientId, ReservationRequestDto reservationRequestDto);
	void deleteReservation(Long id);
	void cancelReservationForClient(Long reservationId, Long clientId);
	Reservation validateReservation(String validationCode) throws NotFoundException, ConflictException, OperationNotAllowedException;

	// Nouvelles méthodes
	List<Reservation> getReservationsByVoyageId(Long voyageId);
	List<Reservation> getReservationsByStatus(String status);
	void verifyReservationOwnership(Long reservationId, Long clientId) throws OperationNotAllowedException;
	Reservation confirmReservationPayment(Long reservationId);
	List<Reservation> searchReservations(String keyword);
	Reservation updateReservationStatus(Long reservationId, String newStatus);
}