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
    TypeChambre typeChambre;

    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    Bloc bloc;

    @ToString.Exclude
    @OneToMany(mappedBy = "chambre")  // Ensure there's a field in Reservation that points to Chambre
    @JsonIgnore
    Set<Reservation> reservations;

    // If you want to maintain a set of chambres, which is unusual here:
    @OneToMany(mappedBy = "bloc")  // Change this mapping if needed
            Set<Chambre> chambres = new HashSet<>();

    public Set<Chambre> getChambres() {
        if (this.chambres == null) {
            this.chambres = new HashSet<>();
        }
        return this.chambres;
    }

}
