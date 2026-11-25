package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.dto.ReservationResponseDTO;
import tn.esprit.autoloc.dto.ReservationWithContratRequestDTO;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
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

    @Override
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = getRequiredById(idReservation);
        Client client = clientRepository.findByNomIgnoreCaseAndPrenomIgnoreCase(nom, prenom)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable: " + nom + " " + prenom));
        reservation.setClient(client);
        return toReservationResponse(repository.save(reservation));
    }

    @Override
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if (requestDTO == null) throw new IllegalArgumentException("Les données de réservation sont obligatoires");
        validateDateRange(requestDTO.getDateDebut(), requestDTO.getDateFin());
        if (requestDTO.getDateSignature() == null) throw new IllegalArgumentException("La date de signature est obligatoire");
        Client client = ServiceSupport.reference(clientRepository, requestDTO.getIdClient(), "Client");
        Vehicule vehicule = ServiceSupport.reference(vehiculeRepository, requestDTO.getIdVehicule(), "Vehicule");

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.getDateDebut());
        reservation.setDateFin(requestDTO.getDateFin());
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        Reservation savedReservation = repository.save(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.getDateSignature());
        contrat.setMontantTotal(calculerMontant(vehicule.getTarifJournalier(), requestDTO.getDateDebut(), requestDTO.getDateFin()));
        contrat.setValide(false);
        contrat.setPaiements(new java.util.ArrayList<>());
        contrat.setReservation(savedReservation);
        Contrat savedContrat = contratRepository.save(contrat);
        savedReservation.setContrat(savedContrat);
        return toReservationResponse(repository.save(savedReservation));
    }

    private BigDecimal calculerMontant(BigDecimal tarifJournalier, java.time.LocalDate debut, java.time.LocalDate fin) {
        if (tarifJournalier == null) throw new IllegalArgumentException("Le tarif journalier du véhicule est obligatoire");
        long nombreJours = ChronoUnit.DAYS.between(debut, fin);
        return tarifJournalier.multiply(BigDecimal.valueOf(nombreJours));
    }

    private void validateDateRange(java.time.LocalDate debut, java.time.LocalDate fin) {
        if (debut == null || fin == null || !fin.isAfter(debut))
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début");
    }

    private ReservationResponseDTO toReservationResponse(Reservation reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setIdReservation(reservation.getIdReservation());
        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());
        dto.setStatut(reservation.getStatut());
        if (reservation.getClient() != null) {
            dto.setClientId(reservation.getClient().getIdClient());
            dto.setNomClient(reservation.getClient().getNom());
            dto.setPrenomClient(reservation.getClient().getPrenom());
        }
        if (reservation.getVehicule() != null) dto.setVehiculeId(reservation.getVehicule().getIdVehicule());
        if (reservation.getContrat() != null) dto.setContratId(reservation.getContrat().getIdContrat());
        return dto;
    }
}
