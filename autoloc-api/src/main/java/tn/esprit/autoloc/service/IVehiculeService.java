package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;
import java.util.Optional;

public interface IVehiculeService {
    List<Vehicule> getAll();

    Optional<Vehicule> getById(Long id);

    Vehicule getRequiredById(Long id);

    Vehicule create(Vehicule entity);

    Vehicule update(Long id, Vehicule entity);

    void delete(Long id);
}
