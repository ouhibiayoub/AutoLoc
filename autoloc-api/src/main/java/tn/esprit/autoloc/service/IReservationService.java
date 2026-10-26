package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    List<Reservation> getAll();
    Optional<Reservation> getById(Long id);
    Reservation save(Reservation reservation);
    void delete(Long id);
}
