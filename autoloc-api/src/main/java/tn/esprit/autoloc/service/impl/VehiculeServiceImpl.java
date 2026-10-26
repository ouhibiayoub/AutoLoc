package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;

    public VehiculeServiceImpl(IVehiculeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Vehicule> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Vehicule> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Vehicule save(Vehicule vehicule) {
        return repository.save(vehicule);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
