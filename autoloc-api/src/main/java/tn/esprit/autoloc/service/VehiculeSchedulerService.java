
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;

import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehiculeSchedulerService {

    private final IReservationRepository reservationRepository;
    private final IVehiculeRepository vehiculeRepository;

    public VehiculeSchedulerService(
            IReservationRepository reservationRepository,
            IVehiculeRepository vehiculeRepository) {

        this.reservationRepository = reservationRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    // Execution tous les jours a 01h00
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void terminerLocations() {

        LocalDate aujourdHui = LocalDate.now();

        List<Reservation> reservations =
                reservationRepository.findAll();

        for (Reservation reservation : reservations) {

            if (reservation.getDateFin() != null
                    && reservation.getDateFin().isBefore(aujourdHui)
                    && reservation.getStatut() == StatutReservation.CONFIRMEE) {

                // Terminer la reservation
                reservation.setStatut(StatutReservation.TERMINEE);

                reservationRepository.save(reservation);

                // Recuperer le vehicule
                Vehicule vehicule = reservation.getVehicule();

                if (vehicule != null) {

                    // Liberer le vehicule
                    vehicule.setStatut(StatutVehicule.DISPONIBLE);

                    vehiculeRepository.save(vehicule);

                    System.out.println(
                            "Vehicule disponible : "
                                    + vehicule.getImmatriculation()
                    );
                }

                System.out.println(
                        "Reservation terminee : "
                                + reservation.getIdReservation()
                );
            }
        }
    }
}
