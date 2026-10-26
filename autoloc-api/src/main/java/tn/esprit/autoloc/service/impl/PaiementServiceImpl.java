package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository repository;

    public PaiementServiceImpl(IPaiementRepository repository) {
        this.repository = repository;
    }

    public List<Paiement> getAll() {
        return repository.findAll();
    }

    public Optional<Paiement> getById(Long id) {
        return repository.findById(id);
    }

    public Paiement getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable: " + id));
    }
}
