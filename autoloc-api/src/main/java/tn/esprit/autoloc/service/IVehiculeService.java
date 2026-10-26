package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import java.util.List;
import java.util.Optional;

public interface IVehiculeService {
    List<Vehicule> getAll();
    Optional<Vehicule> getById(Long id);
    Vehicule save(Vehicule vehicule);
    void delete(Long id);
}
