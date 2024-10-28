package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChambreServiceTest {

    @Autowired
    private ChambreService chambreService;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        chambreRepository.deleteAll(); // Nettoyer les données avant chaque test
        blocRepository.deleteAll();
    }

    @Test
    void testRetrieveAllChambres() {
        // Arrange
        Chambre chambre1 = Chambre.builder().numeroChambre(101L).typeChambre(TypeChambre.SIMPLE).build();
        Chambre chambre2 = Chambre.builder().numeroChambre(102L).typeChambre(TypeChambre.DOUBLE).build();
        chambreRepository.saveAll(Arrays.asList(chambre1, chambre2));

        // Act
        List<Chambre> chambres = chambreService.retrieveAllChambres();

        // Assert
        assertNotNull(chambres);
        assertEquals(2, chambres.size());
        assertEquals(101L, chambres.get(0).getNumeroChambre());
        assertEquals(102L, chambres.get(1).getNumeroChambre());
    }

    @Test
    void testAddChambre() {
        // Arrange
        Chambre chambre = Chambre.builder().numeroChambre(103L).typeChambre(TypeChambre.SIMPLE).build();

        // Act
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Assert
        assertNotNull(savedChambre);
        assertEquals(103L, savedChambre.getNumeroChambre());
    }

    @Test
    void testAffecterChambresABloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("Bloc1").build();
        bloc = blocRepository.save(bloc);

        Chambre chambre1 = Chambre.builder().numeroChambre(101L).build();
        Chambre chambre2 = Chambre.builder().numeroChambre(102L).build();
        chambreRepository.saveAll(Arrays.asList(chambre1, chambre2));

        List<Long> chambreIds = Arrays.asList(101L, 102L);

        // Act
        Bloc affectedBloc = chambreService.affecterChambresABloc(chambreIds, bloc.getIdBloc());

        // Assert
        assertNotNull(affectedBloc);
        assertEquals("Bloc1", affectedBloc.getNomBloc());

        // Vérifie que les chambres sont affectées au bloc
        List<Chambre> chambresAffectees = chambreRepository.findByBlocIdBloc(bloc.getIdBloc());
        assertEquals(2, chambresAffectees.size());
    }


}
