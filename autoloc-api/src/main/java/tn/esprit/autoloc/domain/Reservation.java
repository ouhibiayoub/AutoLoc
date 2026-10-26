package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @NotNull(message = "La date de debut est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut de la reservation est obligatoire")
    private StatutReservation statut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "Le client est obligatoire")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    @NotNull(message = "Le vehicule est obligatoire")
    private Vehicule vehicule;

    @OneToOne
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;
}
