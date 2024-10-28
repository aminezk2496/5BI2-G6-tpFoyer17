package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ChambreServiceTest {

    @Autowired
    private ChambreRepository chambreRepository;

    private ChambreService chambreService;

    @BeforeEach
    public void setUp() {
        chambreService = new ChambreService(chambreRepository, null, null); // Passer des mocks ou null pour les autres repos
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
}