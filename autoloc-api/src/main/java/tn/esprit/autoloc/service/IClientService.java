package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;
import java.util.List;
import java.util.Optional;

public interface IClientService {
    List<Client> getAll();
    Optional<Client> getById(Long id);
    Client save(Client client);
    void delete(Long id);
}
