package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
public class AgenceController {
    private final IAgenceService service;

    public AgenceController(IAgenceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Agence> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agence> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Agence create(@RequestBody Agence agence) {
        return service.save(agence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agence> update(@PathVariable Long id, @RequestBody Agence agence) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        agence.setIdAgence(id);
        return ResponseEntity.ok(service.save(agence));
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
