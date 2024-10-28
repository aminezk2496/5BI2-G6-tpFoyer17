package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BlocServiceTest {

    @InjectMocks
    private BlocService blocService;

    @Mock
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveBlocs() {
        // Arrange
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> blocs = blocService.retrieveBlocs();

        // Assert
        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        assertEquals("Bloc1", blocs.get(0).getNomBloc());
        assertEquals("Bloc2", blocs.get(1).getNomBloc());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc savedBloc = blocService.addBloc(bloc);

        // Assert
        assertNotNull(savedBloc);
        assertEquals("BlocTest", savedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testUpdateBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("UpdatedBloc").build();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc updatedBloc = blocService.updateBloc(bloc);

        // Assert
        assertNotNull(updatedBloc);
        assertEquals("UpdatedBloc", updatedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("Bloc1").build();
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(retrievedBloc);
        assertEquals("Bloc1", retrievedBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteBloc() {
        // Act
        blocService.removeBloc(1L);

        // Assert
        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByFoyerIdFoyer() {
        // Arrange
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        when(blocRepository.findByFoyerIdFoyer(1L)).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L);

        // Assert
        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findByFoyerIdFoyer(1L);
    }

    @Test
    void testFindByChambresIdChambre() {
        // Arrange
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("BlocWithChambre").build();
        when(blocRepository.findByChambresIdChambre(1L)).thenReturn(bloc);

        // Act
        Bloc foundBloc = blocService.findByChambresIdChambre(1L);

        // Assert
        assertNotNull(foundBloc);
        assertEquals("BlocWithChambre", foundBloc.getNomBloc());
        verify(blocRepository, times(1)).findByChambresIdChambre(1L);
    }
}