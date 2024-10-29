package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BlocServiceH2Test {

    @Autowired
    private BlocService blocService;

    @Autowired
    private BlocRepository blocRepository;

    @AfterEach
    void cleanUp() {
        blocRepository.deleteAll();
    }

    @Test
    void testAddBlocAndDelete() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Test Bloc");

        Bloc savedBloc = blocService.addBloc(bloc);
        assertNotNull(savedBloc);
        assertEquals("Test Bloc", savedBloc.getNomBloc());

        blocService.removeBloc(savedBloc.getIdBloc());
        assertFalse(blocRepository.findById(savedBloc.getIdBloc()).isPresent());
    }

    @Test
    void testFindBlocById() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc Example");

        Bloc savedBloc = blocService.addBloc(bloc);
        Bloc foundBloc = blocService.findBlocById(savedBloc.getIdBloc());

        assertNotNull(foundBloc);
        assertEquals("Bloc Example", foundBloc.getNomBloc());
    }
}
