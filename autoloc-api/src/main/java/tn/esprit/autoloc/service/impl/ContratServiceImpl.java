package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;

    public ContratServiceImpl(IContratRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrat> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat introuvable: " + id));
    }

    @Override
    public Contrat create(Contrat entity) {
        ServiceSupport.requireNewId(entity.getIdContrat(), "Contrat");
        if (entity.getPaiements() == null) entity.setPaiements(new ArrayList<>());
        for (Paiement p : entity.getPaiements()) {
            ServiceSupport.requireNewId(p.getIdPaiement(), "Paiement");
            p.setContrat(entity);
        }
        return repository.save(entity);
    }

    @Override
    public Contrat update(Long id, Contrat entity) {
        Contrat existing = getRequiredById(id);
        existing.setDateSignature(entity.getDateSignature());
        existing.setMontantTotal(entity.getMontantTotal());
        existing.setValide(entity.isValide());
        if (entity.getPaiements() != null) {
            for (Paiement p : entity.getPaiements()) {
                ServiceSupport.requireNewId(p.getIdPaiement(), "Paiement");
                p.setContrat(existing);
            }
            existing.getPaiements().clear();
            existing.getPaiements().addAll(entity.getPaiements());
        }
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }
}
