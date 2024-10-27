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
        // Given
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // When
        List<Bloc> blocs = blocService.retrieveBlocs();

        // Then
        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testAddBloc() {
        // Given
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // When
        Bloc savedBloc = blocService.addBloc(bloc);

        // Then
        assertNotNull(savedBloc);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testDeleteBloc() {
        // Given
        Bloc blocToDelete = Bloc.builder().nomBloc("BlocToDelete").build();
        when(blocRepository.findById(blocToDelete.getIdBloc())).thenReturn(Optional.of(blocToDelete));

        // When
        blocService.removeBloc(blocToDelete.getIdBloc());

        // Then
        verify(blocRepository, times(1)).delete(blocToDelete);
    }
}
