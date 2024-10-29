package tn.esprit.tpfoyer17.services.impementations;

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

    @Test
    void testRetrieveBlocs() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertNotNull(blocs, "The retrieved blocs list should not be null");
        assertEquals(2, blocs.size(), "There should be exactly 2 blocs in the list");
    }

    @Test
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();
        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc, "Saved bloc should not be null");
        assertEquals("BlocTest", savedBloc.getNomBloc(), "Bloc name should be 'BlocTest'");
        assertTrue(blocRepository.existsById(savedBloc.getIdBloc()), "Saved bloc should exist in the repository");
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().nomBloc("OldName").build();
        Bloc savedBloc = blocRepository.save(bloc);
        savedBloc.setNomBloc("NewName");

        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        assertNotNull(updatedBloc, "Updated bloc should not be null");
        assertEquals("NewName", updatedBloc.getNomBloc(), "Bloc name should be updated to 'NewName'");
    }

    @Test
    void testRetrieveNonExistentBloc() {
        Bloc bloc = blocService.retrieveBloc(999L);

        assertNull(bloc, "Retrieved bloc should be null for non-existent ID");
    }

    @Test
    void testDeleteBloc() {
        Bloc blocToDelete = Bloc.builder().nomBloc("BlocToDelete").build();
        Bloc savedBloc = blocRepository.save(blocToDelete);

        blocService.removeBloc(savedBloc.getIdBloc());

        assertFalse(blocRepository.existsById(savedBloc.getIdBloc()), "Deleted bloc should not exist in the repository");
    }

    @Test
    void testFindByFoyerIdFoyer() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc1").build();
        blocRepository.save(bloc);

        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L);

        assertTrue(blocs.isEmpty(), "The result should be empty for a non-existent foyer ID");
    }
}
