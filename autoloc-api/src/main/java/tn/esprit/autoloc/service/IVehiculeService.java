package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;
import java.util.List;
import java.util.Optional;

public interface IVehiculeService {
    List<Vehicule> getAll();
    Optional<Vehicule> getById(Long id);
    Vehicule getRequiredById(Long id);
    Vehicule create(Vehicule entity);
    Vehicule update(Long id, Vehicule entity);
    void delete(Long id);

    VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);
    VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation);
}
