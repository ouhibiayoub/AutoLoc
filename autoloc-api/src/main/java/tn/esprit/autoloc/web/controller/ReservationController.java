package tn.esprit.autoloc.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final IReservationService service;

    public ReservationController(IReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reservation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public Reservation create(@RequestBody Reservation reservation) {
        return service.save(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody Reservation reservation) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservation.setIdReservation(id);
        return ResponseEntity.ok(service.save(reservation));
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
