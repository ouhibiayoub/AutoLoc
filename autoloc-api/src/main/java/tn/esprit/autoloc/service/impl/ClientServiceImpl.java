package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;

    public ClientServiceImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Client save(Client client) {
        return repository.save(client);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
