package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChambreServiceMockitoTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreService chambreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddChambre() {
        // Création d'un objet Chambre
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeChambre(TypeChambre.DOUBLE);

        // Simuler le comportement de la méthode save
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Appel de la méthode
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Vérification que la méthode save a été appelée
        verify(chambreRepository, times(1)).save(chambre);

        // Vérification du résultat
        assertEquals(chambre.getNumeroChambre(), savedChambre.getNumeroChambre());
    }

    @Test
    public void testRetrieveChambre() {
        // Création d'un objet Chambre
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeChambre(TypeChambre.SIMPLE);

        // Simuler le comportement de la méthode findById
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Appel de la méthode
        Chambre retrievedChambre = chambreService.retrieveChambre(1L);

        // Vérification du résultat
        assertEquals(chambre.getNumeroChambre(), retrievedChambre.getNumeroChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }
}
