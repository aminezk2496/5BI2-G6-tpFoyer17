package tn.esprit.tpfoyer17.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@RunWith(SpringRunner.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")

class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        // Initialisation de l'entité Foyer pour les tests
        foyer = Foyer.builder()
                .nomFoyer("FoyerTest")
                .adresse("AdresseTest")
                .build();

        Bloc bloc = Bloc.builder()
                .foyer(foyer)
                .build();

        // Sauvegarde des entités pour les tests
        blocRepository.save(bloc);
    }

    @Test
    void testFindByFoyerIdFoyer() {
        List<Bloc> blocs = blocRepository.findByFoyerIdFoyer(foyer.getIdFoyer());
        assertNotNull(blocs);
        assertEquals(1, blocs.size());
        assertEquals(foyer.getIdFoyer(), blocs.get(0).getFoyer().getIdFoyer());
    }

    @Test
    void testFindByChambresIdChambre() {
        // Suppose qu'une chambre est associée à l'ID 1L dans ce bloc
        Bloc bloc = blocRepository.findByChambresIdChambre(1L);
        assertNotNull(bloc);
    }

    @Test
    void testFindByFoyerNomFoyerLikeAndFoyerUniversiteAdresseLike() {
        List<Bloc> blocs = blocRepository.findByFoyerNomFoyerLikeAndFoyerUniversiteAdresseLike("Foyer%", "Adresse%");
        assertNotNull(blocs);
        assertEquals(1, blocs.size());
        assertEquals("FoyerTest", blocs.get(0).getFoyer().getNomFoyer());
    }
}
