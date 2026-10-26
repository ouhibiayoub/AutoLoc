package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    @NotBlank(message = "Le nom de l'employe est obligatoire")
    private String nom;

    @NotBlank(message = "Le prenom de l'employe est obligatoire")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le role de l'employe est obligatoire")
    private RoleEmploye role;

    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;
}
