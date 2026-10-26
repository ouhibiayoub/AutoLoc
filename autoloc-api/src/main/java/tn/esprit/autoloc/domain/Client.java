package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    @NotBlank(message = "Le nom du client est obligatoire")
    private String nom;

    @NotBlank(message = "Le prenom du client est obligatoire")
    private String prenom;

    @Email(message = "L'email doit etre valide")
    private String email;

    private String telephone;

    @NotBlank(message = "Le numero de permis est obligatoire")
    private String numPermis;

    private LocalDate dateInscription;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Reservation> reservations;
}
