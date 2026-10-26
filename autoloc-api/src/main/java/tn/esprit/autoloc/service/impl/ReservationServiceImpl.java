package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;

    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;

    public ReservationServiceImpl(IReservationRepository repository, IClientRepository clientRepository, IVehiculeRepository vehiculeRepository, IContratRepository contratRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.contratRepository = contratRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation introuvable: " + id));
    }

    @Override
    public Reservation create(Reservation entity) {
        ServiceSupport.requireNewId(entity.getIdReservation(), "Reservation");
        entity.setClient(ServiceSupport.reference(clientRepository, entity.getClient() == null ? null : entity.getClient().getIdClient(), "Client"));
        entity.setVehicule(ServiceSupport.reference(vehiculeRepository, entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule(), "Vehicule"));
        entity.setContrat(entity.getContrat() == null ? null : ServiceSupport.reference(contratRepository, entity.getContrat().getIdContrat(), "Contrat"));
        validateDates(entity);
        return repository.save(entity);
    }

    @Override
    public Reservation update(Long id, Reservation entity) {
        Reservation existing = getRequiredById(id);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setStatut(entity.getStatut());
        existing.setClient(ServiceSupport.reference(clientRepository, entity.getClient() == null ? null : entity.getClient().getIdClient(), "Client"));
        existing.setVehicule(ServiceSupport.reference(vehiculeRepository, entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule(), "Vehicule"));
        existing.setContrat(entity.getContrat() == null ? null : ServiceSupport.reference(contratRepository, entity.getContrat().getIdContrat(), "Contrat"));
        validateDates(existing);
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }

    private void validateDates(Reservation value) {
        if (value.getDateFin().isBefore(value.getDateDebut()))
            throw new IllegalArgumentException("La date de fin ne peut pas preceder la date de debut");
    }
}
