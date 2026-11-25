package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import java.time.LocalDate;

import java.util.ArrayList;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;

    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;

    public VehiculeServiceImpl(IVehiculeRepository repository, IAgenceRepository agenceRepository, IEquipementRepository equipementRepository, IMaintenanceRepository maintenanceRepository) {
        this.repository = repository;
        this.agenceRepository = agenceRepository;
        this.equipementRepository = equipementRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicule> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicule getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule introuvable: " + id));
    }

    @Override
    public Vehicule create(Vehicule entity) {
        ServiceSupport.requireNewId(entity.getIdVehicule(), "Vehicule");
        entity.setAgence(entity.getAgence() == null ? null : ServiceSupport.reference(agenceRepository, entity.getAgence().getIdAgence(), "Agence"));
        entity.setEquipements(resolveEquipements(entity.getEquipements()));
        return repository.save(entity);
    }

    @Override
    public Vehicule update(Long id, Vehicule entity) {
        Vehicule existing = getRequiredById(id);
        existing.setImmatriculation(entity.getImmatriculation());
        existing.setMarque(entity.getMarque());
        existing.setModele(entity.getModele());
        existing.setCategorie(entity.getCategorie());
        existing.setTarifJournalier(entity.getTarifJournalier());
        existing.setStatut(entity.getStatut());
        existing.setAgence(entity.getAgence() == null ? null : ServiceSupport.reference(agenceRepository, entity.getAgence().getIdAgence(), "Agence"));
        existing.setEquipements(resolveEquipements(entity.getEquipements()));
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }

    private List<Equipement> resolveEquipements(List<Equipement> values) {
        if (values == null) return new ArrayList<>();
        return values.stream().map(v -> ServiceSupport.reference(equipementRepository, v.getIdEquipement(), "Equipement")).toList();
    }

    @Override
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getRequiredById(idVehicule);
        Equipement equipement = ServiceSupport.reference(equipementRepository, idEquipement, "Equipement");
        if (vehicule.getEquipements() == null) vehicule.setEquipements(new ArrayList<>());
        boolean dejaAffecte = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(idEquipement));
        if (!dejaAffecte) vehicule.getEquipements().add(equipement);
        return toVehiculeDTO(repository.save(vehicule));
    }

    @Override
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("La description de la maintenance est obligatoire");
        if (immatriculation == null || immatriculation.isBlank())
            throw new IllegalArgumentException("L'immatriculation est obligatoire");
        Vehicule vehicule = repository.findByImmatriculationIgnoreCase(immatriculation)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule introuvable: " + immatriculation));
        LocalDate aujourdhui = LocalDate.now();
        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description);
        maintenance.setDateDebut(aujourdhui);
        maintenance.setDateFin(aujourdhui);
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);
        return toVehiculeDTO(vehicule);
    }

    private VehiculeDTO toVehiculeDTO(Vehicule vehicule) {
        VehiculeDTO dto = new VehiculeDTO();
        dto.setIdVehicule(vehicule.getIdVehicule());
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setCategorie(vehicule.getCategorie());
        dto.setStatut(vehicule.getStatut());
        dto.setPrixJournalier(vehicule.getTarifJournalier());
        return dto;
    }
}
