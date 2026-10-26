package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

import java.util.List;
import java.util.Optional;

public interface IAgenceService {
    List<Agence> getAll();
    Optional<Agence> getById(Long id);
    Agence save(Agence agence);
    void delete(Long id);
}
