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
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenances")
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {
    private final IMaintenanceService service;

    @GetMapping
    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances")
    public List<Maintenance> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public Maintenance getById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.getRequiredById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ressource creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public Maintenance create(@Valid @RequestBody Maintenance maintenance) {
        return service.create(maintenance);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource mise a jour"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Maintenance> update(@Parameter(description = "Identifiant") @PathVariable Long id, @Valid @RequestBody Maintenance maintenance) {
        return ResponseEntity.ok(service.update(id, maintenance));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ressource supprimee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
