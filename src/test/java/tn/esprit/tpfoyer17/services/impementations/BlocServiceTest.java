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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    @Test
    @DisplayName("Should retrieve all blocs")
    void testRetrieveBlocs() {
        // Arrange
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        // Act
        List<Bloc> blocs = blocService.retrieveBlocs();

        // Assert
        assertNotNull(blocs, "The retrieved blocs list should not be null");
        assertEquals(2, blocs.size(), "There should be exactly 2 blocs in the list");
    }

    @Test
    @DisplayName("Should add a new bloc")
    void testAddBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();

        // Act
        Bloc savedBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(savedBloc, "Saved bloc should not be null");
        assertEquals("BlocTest", savedBloc.getNomBloc(), "Bloc name should be 'BlocTest'");
        assertTrue(blocRepository.existsById(savedBloc.getIdBloc()), "Saved bloc should exist in the repository");
    }

    @Test
    @DisplayName("Should update an existing bloc")
    void testUpdateBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("OldName").build();
        Bloc savedBloc = blocRepository.save(bloc);
        savedBloc.setNomBloc("NewName");

        // Act
        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        // Assert
        assertNotNull(updatedBloc, "Updated bloc should not be null");
        assertEquals("NewName", updatedBloc.getNomBloc(), "Bloc name should be updated to 'NewName'");
    }

    @Test
    @DisplayName("Should return null for non-existent bloc ID")
    void testRetrieveNonExistentBloc() {
        // Act
        Bloc bloc = blocService.retrieveBloc(999L);

        // Assert
        assertNull(bloc, "Retrieved bloc should be null for non-existent ID");
    }

    @Test
    @DisplayName("Should delete a bloc")
    void testDeleteBloc() {
        // Arrange
        Bloc blocToDelete = Bloc.builder().nomBloc("BlocToDelete").build();
        Bloc savedBloc = blocRepository.save(blocToDelete);

        // Act
        blocService.removeBloc(savedBloc.getIdBloc());

        // Assert
        assertFalse(blocRepository.existsById(savedBloc.getIdBloc()), "Deleted bloc should not exist in the repository");
    }

    @Test
    @DisplayName("Should return empty result for non-existent foyer ID")
    void testFindByFoyerIdFoyer() {
        // Act
        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L);

        // Assert
        assertTrue(blocs.isEmpty(), "The result should be empty for a non-existent foyer ID");
    }
}
