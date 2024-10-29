package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        blocRepository.deleteAll();  // Ensures a clean state before each test
    }

    @Test
    void testRetrieveBlocs_emptyList() {
        List<Bloc> blocs = blocService.retrieveBlocs();
        assertTrue(blocs.isEmpty(), "Blocs list should be empty initially");
    }

    @Test
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();
        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc, "Saved bloc should not be null");
        assertEquals("BlocTest", savedBloc.getNomBloc(), "Bloc name should be 'BlocTest'");

        // Cleanup
        blocService.removeBloc(savedBloc.getIdBloc());
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().nomBloc("OldName").build();
        Bloc savedBloc = blocService.addBloc(bloc);
        savedBloc.setNomBloc("NewName");

        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        assertNotNull(updatedBloc, "Updated bloc should not be null");
        assertEquals("NewName", updatedBloc.getNomBloc(), "Bloc name should be updated to 'NewName'");

        // Cleanup
        blocService.removeBloc(updatedBloc.getIdBloc());
    }

    @Test
    void testRetrieveNonExistentBloc() {
        Bloc bloc = blocService.retrieveBloc(999L);
        assertNull(bloc, "Retrieved bloc should be null for non-existent ID");
    }

    @Test
    void testDeleteBloc() {
        Bloc blocToDelete = Bloc.builder().nomBloc("BlocToDelete").build();
        Bloc savedBloc = blocService.addBloc(blocToDelete);

        blocService.removeBloc(savedBloc.getIdBloc());

        // Verify deletion
        assertNull(blocService.retrieveBloc(savedBloc.getIdBloc()), "Bloc should be removed successfully");
    }

    @Test
    void testFindByFoyerIdFoyer_noBlocs() {
        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L);
        assertTrue(blocs.isEmpty(), "The result should be empty for a non-existent foyer ID");
    }
}
