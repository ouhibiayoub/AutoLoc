package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationResponseDTO;
import tn.esprit.autoloc.dto.ReservationWithContratRequestDTO;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    List<Reservation> getAll();
    Optional<Reservation> getById(Long id);
    Reservation getRequiredById(Long id);
    Reservation create(Reservation entity);
    Reservation update(Long id, Reservation entity);
    void delete(Long id);

    ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom);
    ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
