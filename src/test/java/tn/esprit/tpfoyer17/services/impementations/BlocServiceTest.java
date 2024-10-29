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
import java.util.HashSet;
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
    @DisplayName("Devrait supprimer un bloc et nettoyer les chambres associées")
    void testDeleteBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocÀSupprimer").build();
        bloc.setChambres(new HashSet<>()); // Initialiser le Set de chambres

        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();

        chambre1.setBloc(bloc);
        chambre2.setBloc(bloc);

        bloc.getChambres().add(chambre1);
        bloc.getChambres().add(chambre2);
        bloc = blocRepository.save(bloc);

        blocService.removeBloc(bloc.getIdBloc());

        assertFalse(blocRepository.existsById(bloc.getIdBloc()), "Le bloc supprimé ne doit pas exister dans le dépôt");
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
    @DisplayName("Devrait lever une exception pour la suppression d'un bloc inexistant")
    void testDeleteNonExistentBloc() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.removeBloc(999L);
        });

        assertEquals("Bloc not found", exception.getMessage(), "Le message d'exception doit correspondre");
    }



    @Test
    @DisplayName("Devrait supprimer un bloc en cascade avec ses chambres")
    void testCascadeDeleteBlocWithChambres() {
        Bloc bloc = Bloc.builder().nomBloc("BlocCascade").build();
        bloc.setChambres(new HashSet<>()); // Initialiser le Set de chambres

        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();

        chambre1.setBloc(bloc);
        chambre2.setBloc(bloc);

        bloc.getChambres().add(chambre1);
        bloc.getChambres().add(chambre2);
        bloc = blocRepository.save(bloc);

        blocService.removeBloc(bloc.getIdBloc());

        assertFalse(blocRepository.existsById(bloc.getIdBloc()), "Le bloc supprimé avec cascade ne doit pas exister");
        assertFalse(chambreRepository.existsById(chambre1.getIdChambre()), "La chambre 1 associée ne doit plus exister");
        assertFalse(chambreRepository.existsById(chambre2.getIdChambre()), "La chambre 2 associée ne doit plus exister");
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

    @Test
    @DisplayName("Devrait ajouter un bloc avec une très grande capacité")
    void testAddBlocWithVeryHighCapacity() {
        Bloc bloc = Bloc.builder().nomBloc("BlocLargeCapacity").capaciteBloc(Integer.MAX_VALUE).build();
        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals(Integer.MAX_VALUE, savedBloc.getCapaciteBloc(), "La capacité du bloc doit être la valeur maximale");
    }
    @Test
    @DisplayName("Devrait supprimer un bloc sans chambres associées")
    void testDeleteBlocWithoutChambres() {
        Bloc bloc = Bloc.builder().nomBloc("BlocSansChambres").build();
        bloc = blocRepository.save(bloc);

        blocService.removeBloc(bloc.getIdBloc());
        assertFalse(blocRepository.existsById(bloc.getIdBloc()), "Le bloc sans chambres doit être supprimé");
    }

    @Test
    @DisplayName("Devrait retourner une liste vide lorsque aucun bloc n'existe")
    void testRetrieveAllBlocsWhenNoBlocsExist() {
        List<Bloc> blocs = blocService.retrieveBlocs();
        assertTrue(blocs.isEmpty(), "La liste des blocs doit être vide si aucun bloc n'existe");
    }
    @Test
    @DisplayName("Devrait ajouter un bloc avec un nom de longueur maximale")
    void testAddBlocWithMaxLengthName() {
        String longName = "A".repeat(255); // Assuming 255 is the maximum length allowed
        Bloc bloc = Bloc.builder().nomBloc(longName).build();

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc, "Le bloc sauvegardé ne doit pas être nul");
        assertEquals(longName, savedBloc.getNomBloc(), "Le nom du bloc doit être de longueur maximale");
    }
    @Test
    @DisplayName("Devrait lever une exception pour la mise à jour d'un bloc avec un ID invalide")
    void testUpdateBlocWithInvalidId() {
        Bloc bloc = Bloc.builder().idBloc(-1L).nomBloc("InvalidID").build();

        Exception exception = assertThrows(RuntimeException.class, () -> blocService.updateBloc(1L, bloc));
        assertEquals("Bloc not found with id: -1", exception.getMessage(), "Le message d'exception doit correspondre");
    }

    @Test
    @DisplayName("Devrait retourner une liste vide pour un ID de foyer valide sans blocs")
    void testFindByValidFoyerIdFoyerNoBlocs() {
        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L); // Assuming 1L is a valid ID but with no blocs
        assertTrue(blocs.isEmpty(), "La liste des blocs doit être vide pour un ID de foyer sans blocs");
    }

}
