package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
public class EquipementController {
    private final IEquipementService service;

    public EquipementController(IEquipementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Equipement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Equipement create(@RequestBody Equipement equipement) {
        return service.save(equipement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipement> update(@PathVariable Long id, @RequestBody Equipement equipement) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        equipement.setIdEquipement(id);
        return ResponseEntity.ok(service.save(equipement));
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
