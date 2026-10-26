package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    @NotBlank(message = "Le libelle de l'equipement est obligatoire")
    private String libelle;

    @ManyToMany(mappedBy = "equipements")
    @JsonIgnore
    private List<Vehicule> vehicules;
}
