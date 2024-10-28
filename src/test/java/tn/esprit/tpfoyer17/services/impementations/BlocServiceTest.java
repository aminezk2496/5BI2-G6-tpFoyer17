package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")  // Utiliser le profil test
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        blocRepository.deleteAll();  // Nettoyage de la base avant chaque test
    }

    @AfterEach
    void tearDown() {
        blocRepository.deleteAll();  // Nettoyage de la base après chaque test
    }

    @Test
    void testRetrieveBlocs() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        assertTrue(blocs.contains(bloc1));
        assertTrue(blocs.contains(bloc2));

        // Suppression explicite des éléments créés
        blocRepository.delete(bloc1);
        blocRepository.delete(bloc2);
    }

    @Test
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals("BlocTest", savedBloc.getNomBloc());
        assertTrue(blocRepository.existsById(savedBloc.getIdBloc()));

        // Suppression explicite de l'élément créé
        blocRepository.delete(savedBloc);
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().nomBloc("OldName").build();
        Bloc savedBloc = blocRepository.save(bloc);
        savedBloc.setNomBloc("NewName");

        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        assertNotNull(updatedBloc);
        assertEquals("NewName", updatedBloc.getNomBloc());

        // Suppression explicite de l'élément mis à jour
        blocRepository.delete(updatedBloc);
    }

    @Test
    void testRetrieveNonExistentBloc() {
        Bloc bloc = blocService.retrieveBloc(999L);

        assertNull(bloc);
    }

    @Test
    void testDeleteBloc() {
        Bloc blocToDelete = Bloc.builder().nomBloc("BlocToDelete").build();
        Bloc savedBloc = blocRepository.save(blocToDelete);

        blocService.removeBloc(savedBloc.getIdBloc());

        assertFalse(blocRepository.existsById(savedBloc.getIdBloc()));

        // Pas de suppression nécessaire ici car l'élément a été supprimé dans le test
    }

    @Test
    void testFindByFoyerIdFoyer() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc1").build();
        blocRepository.save(bloc);

        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L);

        assertTrue(blocs.isEmpty());  // Tester avec un foyer qui n'existe pas

        // Suppression explicite de l'élément créé
        blocRepository.delete(bloc);
    }
}
