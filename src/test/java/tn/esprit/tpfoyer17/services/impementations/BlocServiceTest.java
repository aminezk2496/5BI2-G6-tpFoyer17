package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private ChambreRepository chambreRepository;

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

    @Test
    @DisplayName("Devrait mettre à jour un bloc existant")
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().nomBloc("AncienNom").build();
        Bloc savedBloc = blocRepository.save(bloc);
        savedBloc.setNomBloc("NouveauNom");

        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        assertNotNull(updatedBloc, "Le bloc mis à jour ne doit pas être nul");
        assertEquals("NouveauNom", updatedBloc.getNomBloc(), "Le nom du bloc doit être mis à jour à 'NouveauNom'");
    }

    @Test
    @DisplayName("Devrait récupérer un bloc par ID")
    void testRetrieveBloc() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc1").build();
        Bloc savedBloc = blocRepository.save(bloc);

        Bloc retrievedBloc = blocService.retrieveBloc(savedBloc.getIdBloc());

        assertNotNull(retrievedBloc, "Le bloc récupéré ne doit pas être nul");
        assertEquals("Bloc1", retrievedBloc.getNomBloc(), "Le nom du bloc récupéré doit être 'Bloc1'");
    }

    @Test
    @DisplayName("Devrait renvoyer null pour un ID de bloc inexistant")
    void testRetrieveNonExistentBloc() {
        Bloc bloc = blocService.retrieveBloc(999L);
        assertNull(bloc, "Le bloc récupéré doit être nul pour un ID inexistant");
    }

    @Test
    @DisplayName("Devrait supprimer un bloc")
    void testDeleteBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocÀSupprimer").build();
        Bloc savedBloc = blocRepository.save(bloc);

        blocService.removeBloc(savedBloc.getIdBloc());

        assertFalse(blocRepository.existsById(savedBloc.getIdBloc()), "Le bloc supprimé ne doit pas exister dans le dépôt");
    }

    @Test
    @DisplayName("Devrait renvoyer une liste vide pour un ID de foyer inexistant")
    void testFindByFoyerIdFoyer() {
        List<Bloc> blocs = blocService.findByFoyerIdFoyer(999L);
        assertTrue(blocs.isEmpty(), "La liste des blocs doit être vide pour un ID de foyer inexistant");
    }

    @Test
    @DisplayName("Devrait trouver un bloc par l'ID d'une chambre")
    void testFindByChambresIdChambre() {
        // Créez et associez une chambre au bloc
        Bloc bloc = Bloc.builder().nomBloc("BlocAvecChambre").build();
        bloc = blocRepository.save(bloc);

        // Associez la chambre au bloc (en supposant que Chambre est correctement configurée pour la relation)
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setBloc(bloc); // Associe la chambre au bloc
        chambre = chambreRepository.save(chambre); // Sauvegarde de la chambre pour qu'elle ait un ID

        // Act : Cherche le bloc en utilisant l'ID de la chambre
        Bloc foundBloc = blocService.findByChambresIdChambre(chambre.getIdChambre());

        // Assert
        assertNotNull(foundBloc, "Le bloc trouvé ne doit pas être nul");
        assertEquals("BlocAvecChambre", foundBloc.getNomBloc(), "Le nom du bloc doit être 'BlocAvecChambre'");
    }


    @Test
    @DisplayName("Devrait lever une exception pour un bloc non trouvé par ID")
    void testFindBlocByIdThrowsExceptionForNonExistentBloc() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.findBlocById(999L);
        });

        assertEquals("Bloc not found with id: 999", exception.getMessage(), "Le message d'exception doit correspondre");
    }
    @Test
    @DisplayName("Devrait ne pas générer d'erreur lors de la suppression d'un bloc inexistant")
    void testDeleteNonExistentBloc() {
        // Act & Assert
        assertDoesNotThrow(() -> blocService.removeBloc(999L), "La suppression d'un bloc inexistant ne doit pas lever d'exception");
    }


    @Test
    @DisplayName("Devrait supprimer un bloc et ses chambres associées en cascade")
    void testCascadeDeleteBlocWithChambres() {
        Bloc bloc = Bloc.builder().nomBloc("BlocAvecChambres").build();
        bloc = blocRepository.save(bloc);

        Chambre chambre1 = Chambre.builder().numeroChambre(101).bloc(bloc).build();
        Chambre chambre2 = Chambre.builder().numeroChambre(102).bloc(bloc).build();
        chambreRepository.saveAll(Arrays.asList(chambre1, chambre2));

        blocService.removeBloc(bloc.getIdBloc());

        assertFalse(blocRepository.existsById(bloc.getIdBloc()), "Le bloc ne doit plus exister après suppression");
        assertFalse(chambreRepository.existsById(chambre1.getIdChambre()), "La chambre 101 doit être supprimée en cascade");
        assertFalse(chambreRepository.existsById(chambre2.getIdChambre()), "La chambre 102 doit être supprimée en cascade");
    }
    @Test
    @DisplayName("Devrait retourner une valeur par défaut pour un bloc inexistant")
    void testRetrieveNonExistentBlocWithDefault() {
        Bloc defaultBloc = Bloc.builder().nomBloc("DefaultBloc").build();
        Bloc retrievedBloc = Optional.ofNullable(blocService.retrieveBloc(999L)).orElse(defaultBloc);

        assertNotNull(retrievedBloc, "Le bloc retourné ne doit pas être nul");
        assertEquals("DefaultBloc", retrievedBloc.getNomBloc(), "Le bloc par défaut doit avoir le nom 'DefaultBloc'");
    }
    @Test
    @DisplayName("Devrait retourner une liste vide pour un bloc sans chambre")
    void testRetrieveChambresFromBlocWithoutChambres() {
        Bloc bloc = Bloc.builder().nomBloc("BlocSansChambre").build();
        bloc = blocRepository.save(bloc);

        List<Chambre> chambres = chambreRepository.findByBlocIdBloc(bloc.getIdBloc());

        assertTrue(chambres.isEmpty(), "La liste des chambres doit être vide pour un bloc sans chambre");
    }
    @Test
    @DisplayName("Devrait ajouter un bloc avec la capacité minimale de 0")
    void testAddBlocWithMinCapacity() {
        Bloc bloc = Bloc.builder().nomBloc("BlocMinCapacity").capaciteBloc(0).build();
        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc, "Le bloc sauvegardé ne doit pas être nul");
        assertEquals(0, savedBloc.getCapaciteBloc(), "La capacité du bloc doit être 0");
    }

    @Test
    @DisplayName("Devrait ajouter un bloc avec une capacité élevée")
    void testAddBlocWithHighCapacity() {
        int highCapacity = 1000000;
        Bloc bloc = Bloc.builder().nomBloc("BlocHighCapacity").capaciteBloc(highCapacity).build();
        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc, "Le bloc sauvegardé ne doit pas être nul");
        assertEquals(highCapacity, savedBloc.getCapaciteBloc(), "La capacité du bloc doit être la valeur élevée");
    }


}
