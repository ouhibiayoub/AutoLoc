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
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {
    private final IClientService service;

    @GetMapping
    @Operation(summary = "Lister les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients")
    public List<Client> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un(e) client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public Client getById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.getRequiredById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un(e) client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ressource creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public Client create(@Valid @RequestBody Client client) {
        return service.create(client);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un(e) client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource mise a jour"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Client> update(@Parameter(description = "Identifiant") @PathVariable Long id, @Valid @RequestBody Client client) {
        return ResponseEntity.ok(service.update(id, client));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un(e) client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ressource supprimee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
