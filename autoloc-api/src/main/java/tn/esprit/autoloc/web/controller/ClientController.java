package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final IClientService service;

    public ClientController(IClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Client> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Client create(@RequestBody Client client) {
        return service.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        client.setIdClient(id);
        return ResponseEntity.ok(service.save(client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
