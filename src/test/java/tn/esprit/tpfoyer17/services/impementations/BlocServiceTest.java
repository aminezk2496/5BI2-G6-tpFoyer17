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
    @DisplayName("Devrait mettre à jour un bloc existant")
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().nomBloc("AncienNom").build();
        Bloc savedBloc = blocRepository.save(bloc);
        savedBloc.setNomBloc("NouveauNom");

        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        assertNotNull(updatedBloc);
        assertEquals("NouveauNom", updatedBloc.getNomBloc());
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
    @DisplayName("Devrait supprimer un bloc")
    void testDeleteBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocÀSupprimer").build();
        Bloc savedBloc = blocRepository.save(bloc);

        blocService.removeBloc(savedBloc.getIdBloc());

        assertFalse(blocRepository.existsById(savedBloc.getIdBloc()));
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


}

