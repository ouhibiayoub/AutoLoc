package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IContratService {
    List<Contrat> getAll();
    Optional<Contrat> getById(Long id);
    Contrat getRequiredById(Long id);
    Contrat create(Contrat entity);
    Contrat update(Long id, Contrat entity);
    void delete(Long id);

    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
