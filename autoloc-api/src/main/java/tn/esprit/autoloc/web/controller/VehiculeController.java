package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {
    private final IVehiculeService service;

    public VehiculeController(IVehiculeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vehicule> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return service.save(vehicule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vehicule.setIdVehicule(id);
        return ResponseEntity.ok(service.save(vehicule));
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
