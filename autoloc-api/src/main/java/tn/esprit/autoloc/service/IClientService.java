package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    List<Client> getAll();

    Optional<Client> getById(Long id);

    Client getRequiredById(Long id);

    Client create(Client entity);

    Client update(Long id, Client entity);

    void delete(Long id);
}
