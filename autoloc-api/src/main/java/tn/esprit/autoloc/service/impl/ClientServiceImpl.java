package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;


    public ClientServiceImpl(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client introuvable: " + id));
    }

    @Override
    public Client create(Client entity) {
        ServiceSupport.requireNewId(entity.getIdClient(), "Client");

        return repository.save(entity);
    }

    @Override
    public Client update(Long id, Client entity) {
        Client existing = getRequiredById(id);
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setEmail(entity.getEmail());
        existing.setTelephone(entity.getTelephone());
        existing.setNumPermis(entity.getNumPermis());
        existing.setDateInscription(entity.getDateInscription());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }


}
