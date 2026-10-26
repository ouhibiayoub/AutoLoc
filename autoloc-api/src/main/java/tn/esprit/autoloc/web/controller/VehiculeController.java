package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@Tag(name = "Vehicules", description = "Gestion des vehicules de location")
public class VehiculeController {
    private final IVehiculeService service;

    public VehiculeController(IVehiculeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lister les vehicules")
    @ApiResponse(responseCode = "200", description = "Liste des vehicules")
    public List<Vehicule> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule trouve"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    public ResponseEntity<Vehicule> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Creer un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule cree")
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return service.save(vehicule);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule mis a jour"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    public ResponseEntity<Vehicule> update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vehicule.setIdVehicule(id);
        return ResponseEntity.ok(service.save(vehicule));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehicule supprime"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
