package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceTestMockito {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocService blocService;

    private List<Bloc> blocList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        blocList = new ArrayList<>();

        // Initialiser quelques blocs pour les tests
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
        when(blocRepository.findAll()).thenReturn(blocList);

        List<Bloc> result = blocService.retrieveBlocs();
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc Test");

        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc result = blocService.retrieveBloc(1L);
        assertNotNull(result);
        assertEquals(1L, result.getIdBloc());
        assertEquals("Bloc Test", result.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(3L);
        bloc.setNomBloc("Bloc Nouveau");
        bloc.setCapaciteBloc(120);

        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc result = blocService.addBloc(bloc);
        assertNotNull(result);
        assertEquals("Bloc Nouveau", result.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testUpdateBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc Modifié");

        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc updatedBloc = blocService.updateBloc(bloc);
        assertNotNull(updatedBloc);
        assertEquals("Bloc Modifié", updatedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        Long idBloc = 1L;

        doNothing().when(blocRepository).deleteById(idBloc);

        blocService.removeBloc(idBloc);
        verify(blocRepository, times(1)).deleteById(idBloc);
    }
}
