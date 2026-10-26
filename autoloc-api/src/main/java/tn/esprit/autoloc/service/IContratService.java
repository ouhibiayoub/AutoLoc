package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;

import java.util.List;
import java.util.Optional;

public interface IContratService {
    List<Contrat> getAll();

    Optional<Contrat> getById(Long id);

    Contrat getRequiredById(Long id);

    Contrat create(Contrat entity);

    Contrat update(Long id, Contrat entity);

    void delete(Long id);
}
