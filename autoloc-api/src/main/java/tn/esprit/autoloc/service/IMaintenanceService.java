package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;

import java.util.List;
import java.util.Optional;

public interface IMaintenanceService {
    List<Maintenance> getAll();

    Optional<Maintenance> getById(Long id);

    Maintenance getRequiredById(Long id);

    Maintenance create(Maintenance entity);

    Maintenance update(Long id, Maintenance entity);

    void delete(Long id);
}
