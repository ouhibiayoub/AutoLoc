package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
public class ContratController {
    private final IContratService service;

    public ContratController(IContratService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contrat> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrat> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Contrat create(@RequestBody Contrat contrat) {
        return service.save(contrat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrat> update(@PathVariable Long id, @RequestBody Contrat contrat) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        contrat.setIdContrat(id);
        return ResponseEntity.ok(service.save(contrat));
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
