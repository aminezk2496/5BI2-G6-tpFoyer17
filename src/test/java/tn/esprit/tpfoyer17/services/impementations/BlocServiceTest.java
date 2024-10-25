package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceTest implements AutoCloseable {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private tn.esprit.tpfoyer17.services.impementations.BlocService blocService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        // Utilisation de 'openMocks' pour l'initialisation des mocks
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Override
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    void testRetrieveBlocs() {
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc updatedBloc = blocService.updateBloc(bloc);

        assertNotNull(updatedBloc);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        assertNotNull(retrievedBloc);
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveBlocNotFound() {
        when(blocRepository.findById(1L)).thenReturn(Optional.empty());

        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        assertNull(retrievedBloc);
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveBloc() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.removeBloc(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByFoyerIdFoyer() {
        Bloc bloc = new Bloc();
        when(blocRepository.findByFoyerIdFoyer(1L)).thenReturn(Arrays.asList(bloc));

        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L);

        assertNotNull(blocs);
        assertEquals(1, blocs.size());
        verify(blocRepository, times(1)).findByFoyerIdFoyer(1L);
    }

    @Test
    void testFindByChambresIdChambre() {
        Bloc bloc = new Bloc();
        when(blocRepository.findByChambresIdChambre(1L)).thenReturn(bloc);

        Bloc foundBloc = blocService.findByChambresIdChambre(1L);

        assertNotNull(foundBloc);
        verify(blocRepository, times(1)).findByChambresIdChambre(1L);
    }
}
