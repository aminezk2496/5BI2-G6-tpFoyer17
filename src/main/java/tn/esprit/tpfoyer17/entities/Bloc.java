package tn.esprit.tpfoyer17.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bloc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // Prevent manual setting of ID
    long idBloc;

    String nomBloc;

    long capaciteBloc;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY loading
    @JsonIgnore
    Foyer foyer;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Added cascade and LAZY fetch
    Set<Chambre> chambres;
}
