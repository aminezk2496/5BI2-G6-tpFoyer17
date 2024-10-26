package tn.esprit.tpfoyer17.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class ChambreRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChambreRepository chambreRepository;

    @Test
    void testFindByNumeroChambreIn() {
        Chambre chambre1 = Chambre.builder().numeroChambre(101L).build();
        entityManager.persistAndFlush(chambre1);

        Chambre chambre2 = Chambre.builder().numeroChambre(102L).build();
        entityManager.persistAndFlush(chambre2);

        List<Chambre> chambres = chambreRepository.findByNumeroChambreIn(List.of(101L, 102L));

        assertNotNull(chambres);
        assertEquals(2, chambres.size());
    }

    @Test
    void testFindByBlocIdBloc() {
        Bloc bloc = new Bloc();
        entityManager.persistAndFlush(bloc); // idBloc will be generated
        Long generatedId = bloc.getIdBloc();


        Chambre chambre = Chambre.builder().bloc(bloc).build();
        entityManager.persistAndFlush(chambre);

        List<Chambre> chambres = chambreRepository.findByBlocIdBloc(1L);

        assertNotNull(chambres);
        assertEquals(1, chambres.size());
        assertEquals(bloc.getIdBloc(), chambres.get(0).getBloc().getIdBloc());
    }

    @Test
    void testFindByTypeChambreAndReservationsEstValide() {
        Chambre chambre = Chambre.builder().typeChambre(TypeChambre.DOUBLE).build();
        entityManager.persistAndFlush(chambre);

        List<Chambre> chambres = chambreRepository.findByTypeChambreAndReservationsEstValide(TypeChambre.DOUBLE, true);

        assertNotNull(chambres);
        chambres.forEach(c -> assertEquals(TypeChambre.DOUBLE, c.getTypeChambre()));
    }

    @Test
    void testFindByBlocFoyerCapaciteFoyerGreaterThan() {
        // Minimal setup for Foyer with capacity
        Foyer foyer = new Foyer();
        foyer.setCapaciteFoyer(150L);
        entityManager.persistAndFlush(foyer);

        Bloc bloc = new Bloc();
        bloc.setFoyer(foyer);
        entityManager.persistAndFlush(bloc);

        Chambre chambre = Chambre.builder().bloc(bloc).build();
        entityManager.persistAndFlush(chambre);

        List<Chambre> chambres = chambreRepository.findByBlocFoyerCapaciteFoyerGreaterThan(100L);

        assertNotNull(chambres);
        assertEquals(1, chambres.size());
    }

    @Test
    void testFindByBlocFoyerUniversiteNomUniversiteLike() {
        // Minimal setup for Universite
        Universite universite = new Universite();
        universite.setNomUniversite("University Test");
        entityManager.persistAndFlush(universite);

        Foyer foyer = new Foyer();
        foyer.setUniversite(universite);
        entityManager.persistAndFlush(foyer);

        Bloc bloc = new Bloc();
        bloc.setFoyer(foyer);
        entityManager.persistAndFlush(bloc);

        Chambre chambre = Chambre.builder().bloc(bloc).build();
        entityManager.persistAndFlush(chambre);

        List<Chambre> chambres = chambreRepository.findByBlocFoyerUniversiteNomUniversiteLike("University Test");

        assertNotNull(chambres);
        assertEquals(1, chambres.size());
    }

    @Test
    void testGetChambresNonReserveParNomUniversiteEtTypeChambre() {
        Universite universite = new Universite();
        universite.setNomUniversite("University Example");
        entityManager.persistAndFlush(universite);

        Foyer foyer = new Foyer();
        foyer.setUniversite(universite);
        entityManager.persistAndFlush(foyer);

        Bloc bloc = new Bloc();
        bloc.setFoyer(foyer);
        entityManager.persistAndFlush(bloc);

        Chambre chambre = Chambre.builder()
                .bloc(bloc)
                .typeChambre(TypeChambre.SIMPLE)
                .reservations(Collections.emptySet()) // No reservations
                .build();
        entityManager.persistAndFlush(chambre);

        List<Chambre> chambres = chambreRepository.getChambresNonReserveParNomUniversiteEtTypeChambre("University Example", TypeChambre.SIMPLE);

        assertNotNull(chambres);
        assertEquals(1, chambres.size());
    }
}
