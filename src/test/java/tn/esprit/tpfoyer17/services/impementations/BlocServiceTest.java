package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlocServiceTest {

    @Autowired
    private BlocRepository blocRepository; // Assurez-vous que ce dépôt est injecté

    private BlocService blocService;
    private List<Bloc> blocList;

    @BeforeEach
    void setUp() {
        blocList = new ArrayList<>();
        blocService = new BlocService(blocRepository);

        // Optionnel: initialiser quelques blocs pour les tests
        Bloc bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc 1");
        bloc1.setCapaciteBloc(100);

        Bloc bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc 2");
        bloc2.setCapaciteBloc(150);

        blocList.add(bloc1);
        blocList.add(bloc2);
    }

    @Test
    void testRetrieveBlocs() {
        blocRepository.saveAll(blocList); // Enregistrer les blocs initialisés

        List<Bloc> result = blocService.retrieveBlocs();
        assertEquals(2, result.size());
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc Test");
        blocService.addBloc(bloc);

        Bloc result = blocService.retrieveBloc(1L);
        assertNotNull(result);
        assertEquals(1L, result.getIdBloc());
        assertEquals("Bloc Test", result.getNomBloc());
    }

    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(3L);
        bloc.setNomBloc("Bloc Nouveau");
        bloc.setCapaciteBloc(120);

        Bloc result = blocService.addBloc(bloc);
        assertNotNull(result);
        assertEquals("Bloc Nouveau", result.getNomBloc());
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc Modifié");
        blocService.addBloc(bloc);

        bloc.setNomBloc("Bloc Modifié Version 2");
        Bloc updatedBloc = blocService.updateBloc(bloc);
        assertNotNull(updatedBloc);
        assertEquals("Bloc Modifié Version 2", updatedBloc.getNomBloc());
    }

    @Test
    void testRemoveBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc à Supprimer");
        blocService.addBloc(bloc);

        blocService.removeBloc(1L);
        Bloc result = blocService.retrieveBloc(1L);
        assertNull(result);
    }
}
