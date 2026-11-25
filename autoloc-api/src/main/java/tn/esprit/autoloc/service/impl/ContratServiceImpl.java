package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.service.ServiceSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;
    private final IReservationRepository reservationRepository;

    public ContratServiceImpl(IContratRepository repository, IReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrat> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat getRequiredById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat introuvable: " + id));
    }

    @Override
    public Contrat create(Contrat entity) {
        ServiceSupport.requireNewId(entity.getIdContrat(), "Contrat");
        if (entity.getPaiements() == null) entity.setPaiements(new ArrayList<>());
        for (Paiement p : entity.getPaiements()) {
            ServiceSupport.requireNewId(p.getIdPaiement(), "Paiement");
            p.setContrat(entity);
        }
        return repository.save(entity);
    }

    @Override
    public Contrat update(Long id, Contrat entity) {
        Contrat existing = getRequiredById(id);
        existing.setDateSignature(entity.getDateSignature());
        existing.setMontantTotal(entity.getMontantTotal());
        existing.setValide(entity.isValide());
        if (entity.getPaiements() != null) {
            for (Paiement p : entity.getPaiements()) {
                ServiceSupport.requireNewId(p.getIdPaiement(), "Paiement");
                p.setContrat(existing);
            }
            existing.getPaiements().clear();
            existing.getPaiements().addAll(entity.getPaiements());
        }
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getRequiredById(id));
    }

    @Override
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        if (dateSignature == null) throw new IllegalArgumentException("La date de signature est obligatoire");
        if (dateDebut == null || dateFin == null || !dateFin.isAfter(dateDebut))
            throw new IllegalArgumentException("La période de réservation est invalide");
        Reservation reservation = reservationRepository.findFirstByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable pour cette période"));
        if (reservation.getContrat() != null)
            throw new IllegalArgumentException("Cette réservation possède déjà un contrat");
        BigDecimal tarif = reservation.getVehicule().getTarifJournalier();
        if (tarif == null) throw new IllegalArgumentException("Le tarif journalier du véhicule est obligatoire");
        long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setMontantTotal(tarif.multiply(BigDecimal.valueOf(jours)));
        contrat.setValide(false);
        contrat.setPaiements(new ArrayList<>());
        contrat.setReservation(reservation);
        Contrat saved = repository.save(contrat);
        reservation.setContrat(saved); // Reservation est le côté propriétaire du @OneToOne.
        reservationRepository.save(reservation);
        return toDTO(saved);
    }

    private ContratDTO toDTO(Contrat contrat) {
        ContratDTO dto = new ContratDTO();
        dto.setIdContrat(contrat.getIdContrat());
        dto.setDateSignature(contrat.getDateSignature());
        dto.setMontantTotal(contrat.getMontantTotal());
        dto.setValide(contrat.isValide());
        return dto;
    }
}
