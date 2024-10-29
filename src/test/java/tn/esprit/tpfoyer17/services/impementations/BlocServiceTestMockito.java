package tn.esprit.tpfoyer17.services.impementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlocServiceTestMockito {

    @InjectMocks
    private BlocService blocService;

    @Mock
    private BlocRepository blocRepository;

    @Test
    @DisplayName("Should retrieve all blocs using mock")
    void testRetrieveBlocs() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should add a new bloc using mock")
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("BlocTest").build();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals("BlocTest", savedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    @DisplayName("Should update an existing bloc using mock")
    void testUpdateBloc() {
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("UpdatedBloc").build();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc updatedBloc = blocService.updateBloc(bloc);

        assertNotNull(updatedBloc);
        assertEquals("UpdatedBloc", updatedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    @DisplayName("Should retrieve bloc by ID using mock")
    void testRetrieveBloc() {
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("Bloc1").build();
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        assertNotNull(retrievedBloc);
        assertEquals("Bloc1", retrievedBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return null for non-existent bloc ID using mock")
    void testRetrieveNonExistentBloc() {
        when(blocRepository.findById(999L)).thenReturn(Optional.empty());

        Bloc bloc = blocService.retrieveBloc(999L);

        assertNull(bloc);
        verify(blocRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should delete bloc by ID using mock")
    void testDeleteBloc() {
        long blocId = 1L;
        blocService.removeBloc(blocId);

        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    @DisplayName("Should retrieve blocs by foyer ID using mock")
    void testFindByFoyerIdFoyer() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc1").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc2").build();
        when(blocRepository.findByFoyerIdFoyer(1L)).thenReturn(Arrays.asList(bloc1, bloc2));

        List<Bloc> blocs = blocService.findByFoyerIdFoyer(1L);

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findByFoyerIdFoyer(1L);
    }

    @Test
    @DisplayName("Should retrieve bloc by chambre ID using mock")
    void testFindByChambresIdChambre() {
        Bloc bloc = Bloc.builder().idBloc(1L).nomBloc("BlocWithChambre").build();
        when(blocRepository.findByChambresIdChambre(1L)).thenReturn(bloc);

        Bloc foundBloc = blocService.findByChambresIdChambre(1L);

        assertNotNull(foundBloc);
        assertEquals("BlocWithChambre", foundBloc.getNomBloc());
        verify(blocRepository, times(1)).findByChambresIdChambre(1L);
    }
    @Test
    @DisplayName("Should not throw exception when deleting non-existent bloc using mock")
    void testDeleteNonExistentBlocWithMock() {
        // Arrange
        long nonExistentBlocId = 999L;
        doNothing().when(blocRepository).deleteById(nonExistentBlocId);

        // Act & Assert
        assertDoesNotThrow(() -> blocService.removeBloc(nonExistentBlocId));
        verify(blocRepository, times(1)).deleteById(nonExistentBlocId);
    }
    @Test
    @DisplayName("Devrait mettre à jour seulement les champs modifiés dans le bloc")
    void testUpdateBlocFieldsUnchanged() {
        Bloc existingBloc = Bloc.builder().idBloc(1L).nomBloc("AncienNom").capaciteBloc(100).build();
        when(blocRepository.findById(1L)).thenReturn(Optional.of(existingBloc));

        Bloc updatedBloc = existingBloc;
        updatedBloc.setNomBloc("NouveauNom");  // Modification d’un seul champ
        when(blocRepository.save(any(Bloc.class))).thenReturn(updatedBloc);

        Bloc result = blocService.updateBloc(updatedBloc);

        assertEquals("NouveauNom", result.getNomBloc(), "Le nom du bloc doit être mis à jour");
        assertEquals(100, result.getCapaciteBloc(), "La capacité du bloc doit rester inchangée");
        verify(blocRepository, times(1)).save(updatedBloc);
    }

    @Test
    @DisplayName("Devrait lever une exception pour un nom de bloc en double")
    void testDuplicateBlocName() {
        Bloc bloc1 = Bloc.builder().nomBloc("NomUnique").build();
        when(blocRepository.save(bloc1)).thenThrow(new IllegalArgumentException("Nom de bloc en double"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            blocService.addBloc(bloc1);
        });

        assertEquals("Nom de bloc en double", exception.getMessage());
        verify(blocRepository, times(1)).save(bloc1);
    }
    @Test
    @DisplayName("Devrait lever une exception pour la mise à jour d'un bloc inexistant")
    void testUpdateNonExistentBloc() {
        Bloc bloc = Bloc.builder().idBloc(999L).nomBloc("NonExistant").build();
        when(blocRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            blocService.updateBloc(bloc);
        });

        assertEquals("Bloc not found with id: 999", exception.getMessage(), "Le message d'exception doit correspondre");
        verify(blocRepository, never()).save(bloc);
    }


}
