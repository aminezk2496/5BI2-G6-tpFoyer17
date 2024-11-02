package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.Dao.ChambreDao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ChambreServiceMockitoTest {

    @InjectMocks
    private ChambreService chambreService;

    @Mock
    private ChambreDao chambreDao;

    @Mock
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllChambres() {
        // Arrange
        Chambre chambre1 = Chambre.builder().numeroChambre(101L).typeChambre(TypeChambre.SIMPLE).build();
        Chambre chambre2 = Chambre.builder().numeroChambre(102L).typeChambre(TypeChambre.DOUBLE).build();
        when(chambreDao.findAll()).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act
        List<Chambre> chambres = chambreService.retrieveAllChambres();

        // Assert
        assertNotNull(chambres);
        assertEquals(2, chambres.size());
        assertEquals(101L, chambres.get(0).getNumeroChambre());
        assertEquals(102L, chambres.get(1).getNumeroChambre());
        verify(chambreDao, times(1)).findAll();
    }

    @Test
    void testAddChambre() {
        // Arrange
        Chambre chambre = Chambre.builder().numeroChambre(103L).typeChambre(TypeChambre.SIMPLE).build();

        // Act
        chambreService.addChambre(chambre);

        // Assert
        verify(chambreDao, times(1)).save(chambre);  // Check if save was called once with the chambre object
    }

    @Test
    void testAffecterChambresABloc() {
        // Arrange
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("Bloc1").build();
        Chambre chambre1 = Chambre.builder().numeroChambre(101L).build();
        Chambre chambre2 = Chambre.builder().numeroChambre(102L).build();
        List<Long> chambreIds = Arrays.asList(101L, 102L);

        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        when(chambreDao.findByNumeroChambreIn(chambreIds)).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act
        Bloc affectedBloc = chambreService.affecterChambresABloc(chambreIds, 1L);

        // Assert
        assertNotNull(affectedBloc);
        assertEquals("Bloc1", affectedBloc.getNomBloc());
        verify(chambreDao, times(2)).save(any(Chambre.class));
    }
}
