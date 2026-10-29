package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementDTO;

import java.util.List;
import java.util.Optional;

public interface IPaiementService {
    List<Paiement> getAll();

    Optional<Paiement> getById(Long id);

    Paiement getRequiredById(Long id);

    Paiement create(PaiementDTO paiementDTO);
}
