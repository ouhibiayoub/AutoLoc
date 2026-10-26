package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {
    private final IPaiementService service;

    public PaiementController(IPaiementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Paiement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Paiement create(@RequestBody Paiement paiement) {
        return service.save(paiement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paiement> update(@PathVariable Long id, @RequestBody Paiement paiement) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        paiement.setIdPaiement(id);
        return ResponseEntity.ok(service.save(paiement));
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
