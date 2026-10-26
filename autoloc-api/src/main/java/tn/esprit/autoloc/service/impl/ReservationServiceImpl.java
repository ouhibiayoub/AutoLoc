package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;

    public ReservationServiceImpl(IReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Reservation> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Reservation> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
