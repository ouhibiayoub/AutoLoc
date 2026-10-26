package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;


    public AgenceServiceImpl(IAgenceRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agence> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Agence getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence introuvable: " + id));
    }

    @Override
    public Agence create(Agence entity) {
        ServiceSupport.requireNewId(entity.getIdAgence(), "Agence");

        return repository.save(entity);
    }

    @Override
    public Agence update(Long id, Agence entity) {
        Agence existing = getRequiredById(id);
        existing.setNom(entity.getNom());
        existing.setVille(entity.getVille());
        existing.setAdresse(entity.getAdresse());
        existing.setTelephone(entity.getTelephone());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }


}
