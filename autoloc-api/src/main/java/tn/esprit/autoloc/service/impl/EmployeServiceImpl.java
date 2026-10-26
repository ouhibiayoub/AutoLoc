package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository repository;

    public EmployeServiceImpl(IEmployeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employe> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Employe> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Employe save(Employe employe) {
        return repository.save(employe);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
