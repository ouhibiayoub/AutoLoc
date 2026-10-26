package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;
import java.util.List;
import java.util.Optional;

public interface IMaintenanceService {
    List<Maintenance> getAll();
    Optional<Maintenance> getById(Long id);
    Maintenance save(Maintenance maintenance);
    void delete(Long id);
}
