package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** Données nécessaires à la création simultanée d'une réservation et de son contrat. */
public class ReservationWithContratRequestDTO {
    @NotNull private LocalDate dateDebut;
    @NotNull private LocalDate dateFin;
    @NotNull private Long idClient;
    @NotNull private Long idVehicule;
    @NotNull private LocalDate dateSignature;

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }
    public Long getIdVehicule() { return idVehicule; }
    public void setIdVehicule(Long idVehicule) { this.idVehicule = idVehicule; }
    public LocalDate getDateSignature() { return dateSignature; }
    public void setDateSignature(LocalDate dateSignature) { this.dateSignature = dateSignature; }
}
