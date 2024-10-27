package tn.esprit.tpfoyer17.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        foyer.setNomFoyer("FoyerTest");
        foyer.setAdresse("AdresseTest");

        Bloc bloc = new Bloc();
        bloc.setFoyer(foyer);

        blocRepository.save(bloc);
    }

    @Test
    void testFindByFoyerIdFoyer() {
        List<Bloc> blocs = blocRepository.findByFoyerIdFoyer(foyer.getIdFoyer());
        assertNotNull(blocs);
        assertEquals(1, blocs.size());
        assertEquals(foyer.getIdFoyer(), blocs.get(0).getFoyer().getIdFoyer());
    }

    // Autres tests...
}
