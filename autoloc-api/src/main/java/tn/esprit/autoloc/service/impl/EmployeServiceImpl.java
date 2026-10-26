package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository repository;

    private final IAgenceRepository agenceRepository;

    public EmployeServiceImpl(IEmployeRepository repository, IAgenceRepository agenceRepository) {
        this.repository = repository;
        this.agenceRepository = agenceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employe> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Employe getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe introuvable: " + id));
    }

    @Override
    public Employe create(Employe entity) {
        ServiceSupport.requireNewId(entity.getIdEmploye(), "Employe");
        entity.setAgence(ServiceSupport.reference(agenceRepository, entity.getAgence() == null ? null : entity.getAgence().getIdAgence(), "Agence"));
        return repository.save(entity);
    }

    @Override
    public Employe update(Long id, Employe entity) {
        Employe existing = getRequiredById(id);
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setRole(entity.getRole());
        existing.setAgence(ServiceSupport.reference(agenceRepository, entity.getAgence() == null ? null : entity.getAgence().getIdAgence(), "Agence"));
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }


}

