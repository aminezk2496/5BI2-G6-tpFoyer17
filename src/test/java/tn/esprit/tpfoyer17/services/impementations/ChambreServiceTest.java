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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ChambreServiceTest {

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
    public void testAddChambre() {
        // Création d'un objet Chambre
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeChambre(TypeChambre.DOUBLE);

        // Ajout de la chambre
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Vérification que la chambre a été ajoutée
        assertNotNull(savedChambre);
        assertEquals(chambre.getNumeroChambre(), savedChambre.getNumeroChambre());
    }

    @Test
    public void testRetrieveAllChambres() {
        // Ajout de deux chambres
        chambreService.addChambre(new Chambre(102, TypeChambre.SIMPLE.ordinal()));
        chambreService.addChambre(new Chambre(103, TypeChambre.DOUBLE.ordinal()));

        // Récupération de toutes les chambres
        List<Chambre> chambres = chambreService.retrieveAllChambres();

        // Vérification que deux chambres sont présentes
        assertEquals(2, chambres.size());
    }

    @Test
    public void testUpdateChambre() {
        // Création et ajout d'une chambre
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(104);
        chambre.setTypeChambre(TypeChambre.SIMPLE);
        chambreService.addChambre(chambre);

        // Mise à jour de la chambre
        chambre.setTypeChambre(TypeChambre.DOUBLE);
        Chambre updatedChambre = chambreService.updateChambre(chambre);

        // Vérification que la chambre a été mise à jour
        assertNotNull(updatedChambre);
        assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());
    }

    @Test
    public void testRetrieveChambre() {
        // Création et ajout d'une chambre
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(105);
        chambreService.addChambre(chambre);

        // Récupération de la chambre
        Chambre retrievedChambre = chambreService.retrieveChambre(105);

        // Vérification que la chambre a été récupérée
        assertNotNull(retrievedChambre);
        assertEquals(chambre.getNumeroChambre(), retrievedChambre.getNumeroChambre());
    }

    @Test
    public void testRetrieveChambre_NotFound() {
        // Essayer de récupérer une chambre qui n'existe pas
        Chambre retrievedChambre = chambreService.retrieveChambre(999); // ID non existant

        // Vérification que la chambre n'a pas été trouvée
        assertNull(retrievedChambre);
    }

    @Test
    public void testFindByTypeChambre() {
        // Ajout de chambres
        chambreService.addChambre(new Chambre(106, TypeChambre.DOUBLE.ordinal()));
        chambreService.addChambre(new Chambre(107, TypeChambre.SIMPLE.ordinal()));

        // Récupération des chambres de type DOUBLE
        List<Chambre> foundChambres = chambreService.findByTypeChambre();

        // Vérification que la chambre DOUBLE est présente
        assertEquals(1, foundChambres.size());
        assertEquals(TypeChambre.DOUBLE, foundChambres.get(0).getTypeChambre());
    }

    @Test
    public void testAffecterChambresABloc() {
        // Création d'un bloc et ajout de chambres
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        blocRepository.save(bloc); // Enregistrer le bloc

        List<Long> numChambre = List.of(108L, 109L);
        chambreService.addChambre(new Chambre(108, TypeChambre.SIMPLE.ordinal()));
        chambreService.addChambre(new Chambre(109, TypeChambre.DOUBLE.ordinal()));

        // Affectation des chambres au bloc
        Bloc affectedBloc = chambreService.affecterChambresABloc(numChambre, 1L);

        // Vérification que le bloc a bien été affecté
        assertNotNull(affectedBloc);
        assertEquals(bloc.getIdBloc(), affectedBloc.getIdBloc());
    }

    @Test
    public void testGetChambresParNomUniversite() {
        // Ajout d'un bloc et d'une chambre
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        blocRepository.save(bloc); // Enregistrer le bloc

        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(110);
        chambre.setBloc(bloc);
        chambreService.addChambre(chambre);

        // Récupération des chambres par nom d'université
        List<Chambre> foundChambres = chambreService.getChambresParNomUniversite("NomUniversite");

        // Vérification que la chambre est trouvée
        assertEquals(1, foundChambres.size());
        assertEquals(chambre.getNumeroChambre(), foundChambres.get(0).getNumeroChambre());
    }

    @Test
    public void testGetChambresParBlocEtType() {
        // Ajout d'une chambre avec un bloc et type spécifique
        Bloc bloc = new Bloc();
        bloc.setIdBloc(2L);
        blocRepository.save(bloc);

        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(111);
        chambre.setTypeChambre(TypeChambre.DOUBLE);
        chambre.setBloc(bloc);
        chambreService.addChambre(chambre);

        // Récupération des chambres par bloc et type
        List<Chambre> foundChambres = chambreService.getChambresParBlocEtType(2L, TypeChambre.DOUBLE);

        // Vérification que la chambre est trouvée
        assertEquals(1, foundChambres.size());
        assertEquals(chambre.getNumeroChambre(), foundChambres.get(0).getNumeroChambre());
    }
}

