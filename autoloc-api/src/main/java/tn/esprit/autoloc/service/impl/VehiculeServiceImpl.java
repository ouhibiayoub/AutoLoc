package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Equipement;

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

    public VehiculeServiceImpl(IVehiculeRepository repository, IAgenceRepository agenceRepository, IEquipementRepository equipementRepository) {
        this.repository = repository;
        this.agenceRepository = agenceRepository;
        this.equipementRepository = equipementRepository;
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
}
