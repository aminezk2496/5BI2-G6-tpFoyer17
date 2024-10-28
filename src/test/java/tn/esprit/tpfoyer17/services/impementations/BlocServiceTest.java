package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    private Bloc blocToDeleteAfterTest;

    @BeforeEach
    void setUp() {
        blocRepository.deleteAll(); // Nettoyer les données avant chaque test
    }

    @AfterEach
    void tearDown() {
        if (blocToDeleteAfterTest != null && blocRepository.existsById(blocToDeleteAfterTest.getIdBloc())) {
            blocRepository.deleteById(blocToDeleteAfterTest.getIdBloc());
        }
    }

    @Test
    void testRetrieveBlocs() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocRetrieve").build();
        blocToDeleteAfterTest = blocService.addBloc(bloc);

        // Act
        List<Bloc> blocs = blocService.retrieveBlocs();

        // Assert
        assertFalse(blocs.isEmpty());
        assertTrue(blocs.stream().anyMatch(b -> "BlocRetrieve".equals(b.getNomBloc())));
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocTestAdd").build();

        // Act
        Bloc savedBloc = blocService.addBloc(bloc);
        blocToDeleteAfterTest = savedBloc;

        // Assert
        assertNotNull(savedBloc);
        assertEquals("BlocTestAdd", savedBloc.getNomBloc());
    }

    @Test
    void testUpdateBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocOriginal").build();
        Bloc savedBloc = blocService.addBloc(bloc);
        blocToDeleteAfterTest = savedBloc;

        // Act
        savedBloc.setNomBloc("BlocUpdated");
        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        // Assert
        assertEquals("BlocUpdated", updatedBloc.getNomBloc());
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocToRetrieve").build();
        Bloc savedBloc = blocService.addBloc(bloc);
        blocToDeleteAfterTest = savedBloc;

        // Act
        Bloc retrievedBloc = blocService.retrieveBloc(savedBloc.getIdBloc());

        // Assert
        assertNotNull(retrievedBloc);
        assertEquals("BlocToRetrieve", retrievedBloc.getNomBloc());
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocToRemove").build();
        Bloc savedBloc = blocService.addBloc(bloc);

        // Act
        blocService.removeBloc(savedBloc.getIdBloc());

        // Assert
        assertFalse(blocRepository.findById(savedBloc.getIdBloc()).isPresent());
    }

    @Test
    void testFindByFoyerIdFoyer() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocWithFoyer").build();
        blocToDeleteAfterTest = blocService.addBloc(bloc);

        // Act
        List<Bloc> blocs = blocService.findByFoyerIdFoyer(bloc.getIdBloc());

        // Assert
        assertNotNull(blocs);
        assertTrue(blocs.stream().anyMatch(b -> "BlocWithFoyer".equals(b.getNomBloc())));
    }

    @Test
    void testFindByChambresIdChambre() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocWithChambre").build();
        blocToDeleteAfterTest = blocService.addBloc(bloc);

        // Act
        Bloc foundBloc = blocService.findByChambresIdChambre(bloc.getIdBloc());

        // Assert
        assertNotNull(foundBloc);
        assertEquals("BlocWithChambre", foundBloc.getNomBloc());
    }
}
