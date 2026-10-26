package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Equipement;
import java.util.List;
import java.util.Optional;

public interface IEquipementService {
    List<Equipement> getAll();
    Optional<Equipement> getById(Long id);
    Equipement save(Equipement equipement);
    void delete(Long id);
}
