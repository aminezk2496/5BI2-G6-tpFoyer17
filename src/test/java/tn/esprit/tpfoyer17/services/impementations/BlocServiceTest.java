package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Pour s'assurer que les données de test sont isolées
class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        blocRepository.deleteAll(); // Nettoyer la base de données avant chaque test
    }

    @Test
    void testRetrieveBlocs() {
        // Ajouter des données pour le test
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        // Récupérer les blocs via le service
        List<Bloc> blocs = blocService.retrieveBlocs();

        // Vérifications
        assertNotNull(blocs);
        assertEquals(2, blocs.size());
    }

    @Test
    void testAddBloc() {
        // Créer un bloc
        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocTest");

        // Ajouter le bloc via le service
        Bloc savedBloc = blocService.addBloc(bloc);

        // Vérifications
        assertNotNull(savedBloc);
        assertTrue(blocRepository.findById(savedBloc.getIdBloc()).isPresent());
    }

    // Autres tests...
}
