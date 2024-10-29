package tn.esprit.tpfoyer17.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    private Foyer foyer;
    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        // Setup initial data for testing
        Universite universite = new Universite();
        universite.setAdresse("123 University St.");

        foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100L);
        foyer.setUniversite(universite);

        bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(50L);
        bloc.setFoyer(foyer);

        foyerRepository.save(foyer);
        blocRepository.save(bloc);
    }

    @Test
    public void testFindByFoyerIdFoyer() {
        List<Bloc> blocs = blocRepository.findByFoyerIdFoyer(foyer.getIdFoyer());
        assertThat(blocs).isNotEmpty();
        assertThat(blocs.get(0).getNomBloc()).isEqualTo("Bloc A");
    }

    @Test
    public void testFindByChambresIdChambre() {
        // Placeholder for Chambre-related testing
    }

    @Test
    public void testFindByFoyerNomFoyerLikeAndFoyerUniversiteAdresseLike() {
        List<Bloc> blocs = blocRepository.findByFoyerNomFoyerLikeAndFoyerUniversiteAdresseLike("%Foyer%", "%University%");
        assertThat(blocs).isNotEmpty();
        assertThat(blocs.get(0).getNomBloc()).isEqualTo("Bloc A");
    }
}
