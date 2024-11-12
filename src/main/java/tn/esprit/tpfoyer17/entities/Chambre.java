
package tn.esprit.tpfoyer17.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long idChambre;

    long numeroChambre;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_chambre") // Ajout pour mapper correctement le nom de la colonne
    TypeChambre typeChambre;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bloc_id_bloc")
    private Bloc bloc;


    @ToString.Exclude
    @OneToMany
    @JsonIgnore
    Set<Reservation> reservations ;
}
