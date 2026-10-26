package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;

    public MaintenanceServiceImpl(IMaintenanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Maintenance> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Maintenance> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Maintenance save(Maintenance maintenance) {
        return repository.save(maintenance);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
