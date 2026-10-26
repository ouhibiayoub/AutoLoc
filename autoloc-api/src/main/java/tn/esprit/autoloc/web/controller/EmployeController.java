package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {
    private final IEmployeService service;

    public EmployeController(IEmployeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employe> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Employe create(@RequestBody Employe employe) {
        return service.save(employe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employe> update(@PathVariable Long id, @RequestBody Employe employe) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        employe.setIdEmploye(id);
        return ResponseEntity.ok(service.save(employe));
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
