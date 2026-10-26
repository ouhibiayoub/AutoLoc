package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository repository;

    public PaiementServiceImpl(IPaiementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Paiement> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Paiement> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Paiement save(Paiement paiement) {
        return repository.save(paiement);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
