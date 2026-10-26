package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;

    private final IVehiculeRepository vehiculeRepository;

    public MaintenanceServiceImpl(IMaintenanceRepository repository, IVehiculeRepository vehiculeRepository) {
        this.repository = repository;
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Maintenance> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Maintenance getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance introuvable: " + id));
    }

    @Override
    public Maintenance create(Maintenance entity) {
        ServiceSupport.requireNewId(entity.getIdMaintenance(), "Maintenance");
        entity.setVehicule(ServiceSupport.reference(vehiculeRepository, entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule(), "Vehicule"));
        validateDates(entity);
        return repository.save(entity);
    }

    @Override
    public Maintenance update(Long id, Maintenance entity) {
        Maintenance existing = getRequiredById(id);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setDescription(entity.getDescription());
        existing.setCout(entity.getCout());
        existing.setVehicule(ServiceSupport.reference(vehiculeRepository, entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule(), "Vehicule"));
        validateDates(existing);
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }

    private void validateDates(Maintenance value) {
        if (value.getDateFin().isBefore(value.getDateDebut()))
            throw new IllegalArgumentException("La date de fin ne peut pas preceder la date de debut");
    }
}
