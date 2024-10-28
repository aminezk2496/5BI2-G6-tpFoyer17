package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.AfterEach;
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
        blocRepository.deleteAll();  // Ensure a clean state at the start of each test
    }

    @AfterEach
    void tearDown() {
        blocRepository.deleteAll();  // Clean up any data after each test
    }

    @Test
    void testRetrieveBlocs() {
        // Arrange
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        // Act
        List<Bloc> blocs = blocService.retrieveBlocs();

        // Assert
        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        assertEquals("Bloc1", blocs.get(0).getNomBloc());
        assertEquals("Bloc2", blocs.get(1).getNomBloc());

        // Clean up specific records, if needed
        blocRepository.delete(bloc1);
        blocRepository.delete(bloc2);
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();

        // Act
        Bloc savedBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(savedBloc);
        assertEquals("BlocTest", savedBloc.getNomBloc());
        assertTrue(blocRepository.existsById(savedBloc.getIdBloc()));

        // Clean up specific record
        blocRepository.delete(savedBloc);
    }

    @Test
    void testDeleteBloc() {
        // Arrange
        Bloc blocToDelete = Bloc.builder().nomBloc("BlocToDelete").build();
        Bloc savedBloc = blocRepository.save(blocToDelete);

        // Act
        blocService.removeBloc(savedBloc.getIdBloc());

        // Assert
        assertFalse(blocRepository.existsById(savedBloc.getIdBloc()));

        // Clean up is not needed here as the delete is part of the test case
    }
}
