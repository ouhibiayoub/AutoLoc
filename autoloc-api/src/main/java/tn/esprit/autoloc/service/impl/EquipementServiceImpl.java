package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;
import java.util.Optional;

@Service
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;

    public EquipementServiceImpl(IEquipementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Equipement> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Equipement> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Equipement save(Equipement equipement) {
        return repository.save(equipement);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
