
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IContratRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContratSchedulerService {

    private final IContratRepository contratRepository;

    public ContratSchedulerService(IContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    // Execution tous les jours a 07h00
    @Scheduled(cron = "0 0 7 * * *")
    public void verifierContratsArrivantAEcheance() {

        LocalDate aujourdHui = LocalDate.now();
        LocalDate dateLimite = LocalDateTime.now()
                .plusHours(48)
                .toLocalDate();

        List<Contrat> contrats = contratRepository.findAll();

        for (Contrat contrat : contrats) {

            Reservation reservation = contrat.getReservation();

            if (reservation != null
                    && reservation.getDateFin() != null
                    && !reservation.getDateFin().isBefore(aujourdHui)
                    && !reservation.getDateFin().isAfter(dateLimite)) {

                System.out.println(
                        "ATTENTION : Contrat arrivant a echeance !"
                                + " ID Contrat : " + contrat.getIdContrat()
                                + " Date fin : " + reservation.getDateFin()
                );
            }
        }
    }
}
