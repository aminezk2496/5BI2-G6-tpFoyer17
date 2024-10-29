package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  // Ajout de cette ligne
class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    @Test
    @DisplayName("Devrait récupérer tous les blocs")
    void testRetrieveBlocs() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertNotNull(blocs, "La liste des blocs récupérée ne doit pas être nulle");
        assertEquals(2, blocs.size(), "Il doit y avoir exactement 2 blocs dans la liste");
    }

    @Test
    @DisplayName("Devrait ajouter un nouveau bloc")
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc, "Le bloc sauvegardé ne doit pas être nul");
        assertEquals("BlocTest", savedBloc.getNomBloc(), "Le nom du bloc doit être 'BlocTest'");
        assertTrue(blocRepository.existsById(savedBloc.getIdBloc()), "Le bloc sauvegardé doit exister dans le dépôt");
    }

    // Tests supplémentaires pour les autres méthodes
}
