package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChambreServiceTest {

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private UniversiteRepository universiteRepository;

    private ChambreService chambreService;

    @BeforeEach
    public void setUp() {
        chambreService = new ChambreService(chambreRepository, blocRepository, universiteRepository);
    }

    @Test
    void testRetrieveAllChambres() {
        // Arrange
        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(101L);
        chambre1.setTypeChambre(TypeChambre.SIMPLE);
        chambreRepository.save(chambre1);

        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(102L);
        chambre2.setTypeChambre(TypeChambre.DOUBLE);
        chambreRepository.save(chambre2);

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
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(103L);
        chambre.setTypeChambre(TypeChambre.SIMPLE);

        // Act
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Assert
        assertNotNull(savedChambre);
        assertEquals(103L, savedChambre.getNumeroChambre());
        assertTrue(chambreRepository.existsById(savedChambre.getNumeroChambre()));
    }

    @Test
    void testAffecterChambresABloc() {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc1");
        blocRepository.save(bloc);

        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(101L);
        chambreRepository.save(chambre1);

        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(102L);
        chambreRepository.save(chambre2);

        List<Long> chambreIds = List.of(101L, 102L);

        // Act
        Bloc affectedBloc = chambreService.affecterChambresABloc(chambreIds, 1L);

        // Assert
        assertNotNull(affectedBloc);
        assertEquals("Bloc1", affectedBloc.getNomBloc());
        Optional<Chambre> chambreAffectee1 = chambreRepository.findById(101L);
        Optional<Chambre> chambreAffectee2 = chambreRepository.findById(102L);
        assertTrue(chambreAffectee1.isPresent() && chambreAffectee1.get().getBloc().equals(bloc));
        assertTrue(chambreAffectee2.isPresent() && chambreAffectee2.get().getBloc().equals(bloc));
    }

    @Test
    void testGetChambresParNomUniversite() {
        // Arrange
        String nomUniversite = "Universite1";
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc1");
        blocRepository.save(bloc);

        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setBloc(bloc);
        chambreRepository.save(chambre);

        // Act
        List<Chambre> chambres = chambreService.getChambresParNomUniversite(nomUniversite);

        // Assert
        assertNotNull(chambres);
        assertEquals(1, chambres.size());
        assertEquals(101L, chambres.get(0).getNumeroChambre());
    }
}
