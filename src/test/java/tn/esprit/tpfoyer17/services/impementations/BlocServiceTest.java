package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BlocServiceTest {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    private Bloc blocToDeleteAfterTest;

    @BeforeEach
    void setUp() {
        blocRepository.deleteAll();
        foyerRepository.deleteAll();
        chambreRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        if (blocToDeleteAfterTest != null && blocRepository.existsById(blocToDeleteAfterTest.getIdBloc())) {
            blocRepository.deleteById(blocToDeleteAfterTest.getIdBloc());
        }
    }

    @Test
    void testRetrieveBlocs() {
        Bloc bloc = Bloc.builder().nomBloc("BlocRetrieve").build();
        blocToDeleteAfterTest = blocService.addBloc(bloc);

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertFalse(blocs.isEmpty());
        assertTrue(blocs.stream().anyMatch(b -> "BlocRetrieve".equals(b.getNomBloc())));
    }

    @Test
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTestAdd").build();

        Bloc savedBloc = blocService.addBloc(bloc);
        blocToDeleteAfterTest = savedBloc;

        assertNotNull(savedBloc);
        assertEquals("BlocTestAdd", savedBloc.getNomBloc());
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocOriginal").build();
        Bloc savedBloc = blocService.addBloc(bloc);
        blocToDeleteAfterTest = savedBloc;

        savedBloc.setNomBloc("BlocUpdated");
        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        assertEquals("BlocUpdated", updatedBloc.getNomBloc());
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocToRetrieve").build();
        Bloc savedBloc = blocService.addBloc(bloc);
        blocToDeleteAfterTest = savedBloc;

        Bloc retrievedBloc = blocService.retrieveBloc(savedBloc.getIdBloc());

        assertNotNull(retrievedBloc);
        assertEquals("BlocToRetrieve", retrievedBloc.getNomBloc());
    }

    @Test
    void testRemoveBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocToRemove").build();
        Bloc savedBloc = blocService.addBloc(bloc);

        blocService.removeBloc(savedBloc.getIdBloc());

        assertFalse(blocRepository.findById(savedBloc.getIdBloc()).isPresent());
    }

    @Test
    void testFindByFoyerIdFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer Test");
        foyerRepository.save(foyer);

        Bloc bloc = Bloc.builder().nomBloc("BlocWithFoyer").foyer(foyer).build();
        blocToDeleteAfterTest = blocService.addBloc(bloc);

        List<Bloc> blocs = blocService.findByFoyerIdFoyer(foyer.getIdFoyer());

        assertNotNull(blocs);
        assertFalse(blocs.isEmpty());
        assertTrue(blocs.stream().anyMatch(b -> "BlocWithFoyer".equals(b.getNomBloc())));
    }

    @Test
    void testFindByChambresIdChambre() {
        // Arrange : Création du Foyer et du Bloc associé
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer Test");
        foyerRepository.save(foyer);

        Bloc bloc = Bloc.builder().nomBloc("BlocWithChambre").foyer(foyer).build();
        blocToDeleteAfterTest = blocService.addBloc(bloc);

        // Associer et enregistrer la Chambre avec le Bloc
        Chambre chambre = new Chambre();
        chambre.setBloc(bloc);  // Associe la chambre au bloc
        chambreRepository.save(chambre);

        // Act : Recherchez le Bloc via l'ID de la Chambre
        Bloc foundBloc = blocService.findByChambresIdChambre(chambre.getIdChambre());

        // Assert
        assertNotNull(foundBloc, "Bloc trouvé devrait ne pas être nul");
        assertEquals("BlocWithChambre", foundBloc.getNomBloc());
    }
}
