package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface IReservationService {
    List<Reservation> getAll();

    Optional<Reservation> getById(Long id);

    Reservation getRequiredById(Long id);

    Reservation create(Reservation entity);

    Reservation update(Long id, Reservation entity);

    void delete(Long id);
}
