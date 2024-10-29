package tn.esprit.tpfoyer17.services.impementations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BlocServiceMockitoTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocService blocService;

    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc();  // assuming a default constructor or set necessary properties here
        bloc.setNomBloc("Test Bloc");

        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals("Test Bloc", savedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(any(Bloc.class));
    }

    @Test
    void testFindBlocById() {
        Bloc bloc = new Bloc();
        bloc.getIdBloc();
        bloc.setNomBloc("Bloc 1");

        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc foundBloc = blocService.findBlocById(1L);

        assertNotNull(foundBloc);
        assertEquals("Bloc 1", foundBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }
}
