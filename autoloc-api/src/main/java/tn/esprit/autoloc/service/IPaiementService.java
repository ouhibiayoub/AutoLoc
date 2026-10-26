package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;
import java.util.List;
import java.util.Optional;

public interface IPaiementService {
    List<Paiement> getAll();
    Optional<Paiement> getById(Long id);
    Paiement save(Paiement paiement);
    void delete(Long id);
}
