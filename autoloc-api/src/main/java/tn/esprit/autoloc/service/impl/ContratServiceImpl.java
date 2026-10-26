package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;
import java.util.Optional;

@Service
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;

    public ContratServiceImpl(IContratRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Contrat> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Contrat> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Contrat save(Contrat contrat) {
        return repository.save(contrat);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
