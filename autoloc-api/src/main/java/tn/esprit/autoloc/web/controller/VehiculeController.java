package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicules")
@Tag(name = "Vehicules", description = "Gestion des vehicules")
public class VehiculeController {
    private final IVehiculeService service;

    @GetMapping
    @Operation(summary = "Lister les vehicules")
    @ApiResponse(responseCode = "200", description = "Liste des vehicules")
    public List<Vehicule> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public Vehicule getById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.getRequiredById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ressource creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public Vehicule create(@Valid @RequestBody Vehicule vehicule) {
        return service.create(vehicule);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource mise a jour"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Vehicule> update(@Parameter(description = "Identifiant") @PathVariable Long id, @Valid @RequestBody Vehicule vehicule) {
        return ResponseEntity.ok(service.update(id, vehicule));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ressource supprimee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
