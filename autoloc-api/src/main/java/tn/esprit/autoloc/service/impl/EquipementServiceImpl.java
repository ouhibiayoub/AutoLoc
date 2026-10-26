package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;


    public EquipementServiceImpl(IEquipementRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Equipement> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipement getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement introuvable: " + id));
    }

    @Override
    public Equipement create(Equipement entity) {
        ServiceSupport.requireNewId(entity.getIdEquipement(), "Equipement");

        return repository.save(entity);
    }

    @Override
    public Equipement update(Long id, Equipement entity) {
        Equipement existing = getRequiredById(id);
        existing.setLibelle(entity.getLibelle());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }


}
