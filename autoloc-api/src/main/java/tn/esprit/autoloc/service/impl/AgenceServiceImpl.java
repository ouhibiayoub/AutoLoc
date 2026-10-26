package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;
import java.util.Optional;

@Service
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;

    public AgenceServiceImpl(IAgenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Agence> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Agence> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Agence save(Agence agence) {
        return repository.save(agence);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
