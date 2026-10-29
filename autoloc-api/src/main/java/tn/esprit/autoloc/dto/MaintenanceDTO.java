
package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MaintenanceDTO {

    private Long idMaintenance;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "La date de maintenance est obligatoire")
    private LocalDate dateMaintenance;

    @PositiveOrZero(message = "Le coût doit être positif ou nul")
    private BigDecimal cout;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long vehiculeId;

    public Long getIdMaintenance() { return idMaintenance; }
    public void setIdMaintenance(Long idMaintenance) { this.idMaintenance = idMaintenance; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateMaintenance() { return dateMaintenance; }
    public void setDateMaintenance(LocalDate dateMaintenance) { this.dateMaintenance = dateMaintenance; }

    public BigDecimal getCout() { return cout; }
    public void setCout(BigDecimal cout) { this.cout = cout; }

    public Long getVehiculeId() { return vehiculeId; }
    public void setVehiculeId(Long vehiculeId) { this.vehiculeId = vehiculeId; }
}
