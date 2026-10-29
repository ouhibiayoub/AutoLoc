package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.ServiceSupport;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository repository;
    private final IContratRepository contratRepository;

    public PaiementServiceImpl(IPaiementRepository repository, IContratRepository contratRepository) {
        this.repository = repository;
        this.contratRepository = contratRepository;
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

    @Override
    @Transactional
    public Paiement create(PaiementDTO paiementDTO) {
        Contrat contrat = ServiceSupport.reference(contratRepository, paiementDTO.getContratId(), "Contrat");
        Paiement paiement = new Paiement();
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setDatePaiement(paiementDTO.getDatePaiement());
        paiement.setModePaiement(paiementDTO.getModePaiement());
        paiement.setContrat(contrat);
        return repository.save(paiement);
    }
}
