package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {
    private final IMaintenanceService service;

    public MaintenanceController(IMaintenanceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Maintenance> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return service.save(maintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> update(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        maintenance.setIdMaintenance(id);
        return ResponseEntity.ok(service.save(maintenance));
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
