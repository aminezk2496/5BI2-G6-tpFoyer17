package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlocServiceTest {

    @InjectMocks
    private BlocService blocService;

    @Mock
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);  // Mock save method

        // Act
        Bloc savedBloc = blocService.addBloc(bloc);  // Call the method under test

        // Assert
        assertNotNull(savedBloc);
        assertEquals("BlocTest", savedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);  // Verify save was called once
    }

    @Test
    void testDeleteBloc() {
        // Arrange
        Bloc blocToDelete = Bloc.builder().idBloc(1L).nomBloc("BlocToDelete").build();
        when(blocRepository.findById(1L)).thenReturn(Optional.of(blocToDelete));  // Mock findById method

        // Act
        blocService.removeBloc(1L);

        // Assert
        verify(blocRepository, times(1)).findById(1L);  // Verify findById was called once
        verify(blocRepository, times(1)).deleteById(1L);  // Verify deleteById was called once
    }
}
