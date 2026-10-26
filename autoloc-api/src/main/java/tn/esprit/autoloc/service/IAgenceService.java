package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

import java.util.List;
import java.util.Optional;

public interface IAgenceService {
    List<Agence> getAll();

    Optional<Agence> getById(Long id);

    Agence getRequiredById(Long id);

    Agence create(Agence entity);

    Agence update(Long id, Agence entity);

    void delete(Long id);
}
