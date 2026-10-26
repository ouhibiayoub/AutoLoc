package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;
import java.util.List;
import java.util.Optional;

public interface IEmployeService {
    List<Employe> getAll();
    Optional<Employe> getById(Long id);
    Employe save(Employe employe);
    void delete(Long id);
}
