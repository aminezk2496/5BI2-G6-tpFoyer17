package tn.esprit.tpfoyer17.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        // Nettoyer la base de données avant chaque test
        blocRepository.deleteAll();
        foyerRepository.deleteAll();

        // Créer et enregistrer un foyer pour les tests
        foyer = new Foyer();
        foyer.setNomFoyer("FoyerTest");
        foyer.setAdresse("AdresseTest");
        foyer = foyerRepository.save(foyer); // Sauvegarder le foyer et obtenir l'ID généré

        // Vérifier que l'ID du foyer est bien défini
        assertNotNull(foyer.getIdFoyer());

        // Créer et enregistrer un bloc associé au foyer
        Bloc bloc = new Bloc();
        bloc.setFoyer(foyer);
        blocRepository.save(bloc);
    }

    @Test
    void testFindByFoyerIdFoyer() {
        // Récupérer les blocs associés au foyer
        List<Bloc> blocs = blocRepository.findByFoyerIdFoyer(foyer.getIdFoyer());

        // Vérifications
        assertNotNull(blocs);
        assertEquals(1, blocs.size());
        assertEquals(foyer.getIdFoyer(), blocs.get(0).getFoyer().getIdFoyer());
    }

    @Test
    void testSaveAndFindById() {
        // Créer un nouveau bloc
        Bloc newBloc = new Bloc();
        newBloc.setFoyer(foyer);

        // Enregistrer le bloc
        Bloc savedBloc = blocRepository.save(newBloc);

        // Récupérer le bloc par son ID
        Optional<Bloc> foundBloc = blocRepository.findById(savedBloc.getIdBloc());

        // Vérifications
        assertTrue(foundBloc.isPresent());
        assertEquals(savedBloc.getIdBloc(), foundBloc.get().getIdBloc());
    }

    @Test
    void testDeleteBloc() {
        // Créer et enregistrer un nouveau bloc
        Bloc blocToDelete = new Bloc();
        blocToDelete.setFoyer(foyer);
        blocToDelete = blocRepository.save(blocToDelete);

        // Supprimer le bloc
        blocRepository.delete(blocToDelete);

        // Vérifier que le bloc a été supprimé
        Optional<Bloc> foundBloc = blocRepository.findById(blocToDelete.getIdBloc());
        assertFalse(foundBloc.isPresent());
    }

    // Autres tests...
}
