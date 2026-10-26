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
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "Reservations", description = "Gestion des reservations")
public class ReservationController {
    private final IReservationService service;

    @GetMapping
    @Operation(summary = "Lister les reservations")
    @ApiResponse(responseCode = "200", description = "Liste des reservations")
    public List<Reservation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public Reservation getById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.getRequiredById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ressource creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public Reservation create(@Valid @RequestBody Reservation reservation) {
        return service.create(reservation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource mise a jour"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Reservation> update(@Parameter(description = "Identifiant") @PathVariable Long id, @Valid @RequestBody Reservation reservation) {
        return ResponseEntity.ok(service.update(id, reservation));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ressource supprimee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
