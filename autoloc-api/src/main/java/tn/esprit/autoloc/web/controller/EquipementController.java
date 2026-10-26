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
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipements")
@Tag(name = "Equipements", description = "Gestion des equipements")
public class EquipementController {
    private final IEquipementService service;

    @GetMapping
    @Operation(summary = "Lister les equipements")
    @ApiResponse(responseCode = "200", description = "Liste des equipements")
    public List<Equipement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public Equipement getById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.getRequiredById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ressource creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public Equipement create(@Valid @RequestBody Equipement equipement) {
        return service.create(equipement);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource mise a jour"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Equipement> update(@Parameter(description = "Identifiant") @PathVariable Long id, @Valid @RequestBody Equipement equipement) {
        return ResponseEntity.ok(service.update(id, equipement));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ressource supprimee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
