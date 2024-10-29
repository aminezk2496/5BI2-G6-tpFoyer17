package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
    }

    @Test
    @DisplayName("Devrait ajouter un nouveau bloc")
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals("BlocTest", savedBloc.getNomBloc());
        assertTrue(blocRepository.existsById(savedBloc.getIdBloc()));
    }



    @Test
    @DisplayName("Devrait récupérer un bloc par ID")
    void testRetrieveBloc() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc1").build();
        Bloc savedBloc = blocRepository.save(bloc);

        Bloc retrievedBloc = blocService.retrieveBloc(savedBloc.getIdBloc());

        assertNotNull(retrievedBloc);
        assertEquals("Bloc1", retrievedBloc.getNomBloc());
    }

    @Test
    @DisplayName("Devrait renvoyer null pour un ID de bloc inexistant")
    void testRetrieveNonExistentBloc() {
        Bloc bloc = blocService.retrieveBloc(999L);
        assertNull(bloc);
    }



    @Test
    @DisplayName("Devrait lever une exception pour un bloc non trouvé par ID")
    void testFindBlocByIdThrowsExceptionForNonExistentBloc() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.findBlocById(999L);
        });

        assertEquals("Bloc not found with id: 999", exception.getMessage());
    }
    @Test
    @DisplayName("Devrait lever une exception pour un bloc non trouvé lors de la mise à jour")
    void testUpdateNonExistentBloc() {
        Bloc bloc = Bloc.builder().idBloc(999L).nomBloc("NomInconnu").build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.updateBloc(bloc);
        });

        assertEquals("Bloc not found with id: 999", exception.getMessage());
    }

    @Test
    @DisplayName("Devrait lever une exception lors de la suppression d'un bloc inexistant")
    void testDeleteNonExistentBloc() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.removeBloc(999L);
        });

        assertEquals("Bloc not found", exception.getMessage());
    }

    @Test
    @DisplayName("Devrait lever une exception lors de la recherche d'un bloc par ID invalide")
    void testFindBlocByIdThrowsExceptionForInvalidId() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.findBlocById(-1L);
        });

        assertEquals("Bloc not found with id: -1", exception.getMessage());
    }

    @Test
    @DisplayName("Devrait lever une exception lors de l'ajout d'un bloc sans nom")
    void testAddBlocWithoutName() {
        Bloc bloc = Bloc.builder().build(); // No name

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            blocService.addBloc(bloc);
        });

        assertEquals("Bloc name cannot be null", exception.getMessage());
    }
    @Test
    @DisplayName("Should return an empty list when no blocs exist")
    void testRetrieveBlocsWhenNoBlocsExist() {
        List<Bloc> blocs = blocService.retrieveBlocs();
        assertNotNull(blocs, "The list of blocs should not be null");
        assertTrue(blocs.isEmpty(), "The list of blocs should be empty");
    }
    @Test
    @DisplayName("Should add a bloc successfully")
    void testAddValidBloc() {
        Bloc bloc = Bloc.builder().nomBloc("ValidBloc").build();
        blocService.addBloc(bloc); // Ensure it does not throw any exceptions
        assertTrue(blocRepository.existsById(bloc.getIdBloc()), "The bloc should exist in the repository after saving");
    }
    @Test
    @DisplayName("Should update an existing bloc")
    void testUpdateExistingBloc() {
        Bloc bloc = Bloc.builder().nomBloc("OldName").build();
        bloc = blocRepository.save(bloc); // Save to get an ID
        bloc.setNomBloc("UpdatedName");

        Bloc updatedBloc = blocService.updateBloc(bloc);
        assertEquals("UpdatedName", updatedBloc.getNomBloc(), "The bloc name should be updated");
    }
    @Test
    @DisplayName("Should retrieve a bloc by its ID")
    void testRetrieveBlocById() {
        Bloc bloc = Bloc.builder().nomBloc("BlocToRetrieve").build();
        bloc = blocRepository.save(bloc); // Save to get an ID

        Bloc retrievedBloc = blocService.retrieveBloc(bloc.getIdBloc());
        assertNotNull(retrievedBloc, "The retrieved bloc should not be null");
        assertEquals("BlocToRetrieve", retrievedBloc.getNomBloc(), "The bloc name should match the expected name");
    }
    @Test
    @DisplayName("Should add a bloc and retrieve it")
    void testAddAndRetrieveBloc() {
        Bloc bloc = Bloc.builder().nomBloc("TestBloc").build();
        blocService.addBloc(bloc);

        Bloc retrievedBloc = blocService.retrieveBloc(bloc.getIdBloc());

        assertNotNull(retrievedBloc);
        assertEquals("TestBloc", retrievedBloc.getNomBloc());
    }

    @Test
    @DisplayName("Should update an existing bloc")
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().nomBloc("InitialBloc").build();
        bloc = blocService.addBloc(bloc); // Save it first

        bloc.setNomBloc("UpdatedBloc");
        blocService.updateBloc(bloc); // Update it

        Bloc updatedBloc = blocService.retrieveBloc(bloc.getIdBloc());

        assertNotNull(updatedBloc);
        assertEquals("UpdatedBloc", updatedBloc.getNomBloc());
    }

    @Test
    @DisplayName("Should delete an existing bloc")
    void testDeleteBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocToDelete").build();
        bloc = blocService.addBloc(bloc);

        blocService.removeBloc(bloc.getIdBloc());

        Bloc deletedBloc = blocService.retrieveBloc(bloc.getIdBloc());
        assertNull(deletedBloc, "Bloc should not be found after deletion");
    }
    @Test
    @DisplayName("Should throw exception when updating a bloc with null ID")
    void testUpdateBlocWithNullId() {
        Bloc bloc = Bloc.builder().idBloc(null).nomBloc("BlocSansId").build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.updateBloc(bloc);
        });

        assertEquals("Bloc not found with id: null", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle adding multiple blocs")
    void testAddMultipleBlocs() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();

        blocService.addBloc(bloc1);
        blocService.addBloc(bloc2);

        assertEquals(2, blocRepository.findAll().size(), "There should be 2 blocs in the repository");
    }

    @Test
    @DisplayName("Should retrieve all blocs")
    void testRetrieveAllBlocs() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();

        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertEquals(2, blocs.size(), "Should retrieve two blocs");
    }

    @Test
    @DisplayName("Should update the capacity of a bloc")
    void testUpdateBlocCapacity() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").capaciteBloc(5L).build();
        bloc = blocRepository.save(bloc);

        bloc.setCapaciteBloc(10L);
        blocService.updateBloc(bloc);

        Bloc updatedBloc = blocService.retrieveBloc(bloc.getIdBloc());
        assertEquals(10L, updatedBloc.getCapaciteBloc(), "The bloc's capacity should be updated to 10");
    }
    @Test
    @DisplayName("Should throw exception when trying to update a bloc to have a null name")
    void testUpdateBlocToNullName() {
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("ValidName").build();
        blocRepository.save(bloc);

        bloc.setNomBloc(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            blocService.updateBloc(bloc);
        });

        assertEquals("Bloc name cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve bloc with correct associations")
    void testRetrieveBlocWithChambres() {
        // Setup
        Bloc bloc = Bloc.builder().nomBloc("BlocWithChambres").build();
        bloc = blocRepository.save(bloc);

        // Assume chambre is associated
        // Create chambre entity and associate it with bloc, if applicable

        Bloc retrievedBloc = blocService.findBlocById(bloc.getIdBloc());
        assertNotNull(retrievedBloc);
        assertEquals("BlocWithChambres", retrievedBloc.getNomBloc());
    }

    @Test
    @DisplayName("Should handle adding multiple blocs and retrieving them")
    void testAddMultipleBlocsAndRetrieve() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();

        blocService.addBloc(bloc1);
        blocService.addBloc(bloc2);

        assertEquals(2, blocRepository.findAll().size(), "Should retrieve two blocs");
    }

    @Test
    @DisplayName("Should update an existing bloc's name successfully")
    void testUpdateBlocName() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc1").build();
        bloc = blocRepository.save(bloc);

        bloc.setNomBloc("UpdatedBloc");
        blocService.updateBloc(bloc);

        Bloc updatedBloc = blocService.retrieveBloc(bloc.getIdBloc());
        assertEquals("UpdatedBloc", updatedBloc.getNomBloc());
    }

    @Test
    @DisplayName("Should throw exception when trying to retrieve non-existent bloc by ID")
    void testRetrieveNonExistentBlocById() {
        long nonExistentId = 999L;
        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.findBlocById(nonExistentId);
        });

        assertEquals("Bloc not found with id: " + nonExistentId, exception.getMessage());
    }

}



