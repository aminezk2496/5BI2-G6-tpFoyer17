package tn.esprit.tpfoyer17.services.impementations;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.interfaces.IFoyerService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoyerService implements IFoyerService {
    FoyerRepository foyerRepository;
    BlocRepository blocRepository;
    UniversiteRepository universiteRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        return (List<Foyer>) foyerRepository.findAll();
    }

    @Override
    public Foyer addFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer updateFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer retrieveFoyer(long idFoyer) {
        return foyerRepository.findById(idFoyer).orElse(null);
    }

    @Override
    public void removeFoyer(long idFoyer) {
    foyerRepository.deleteById(idFoyer);
    }

    @Transactional
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        // Récupérer l'université à partir de l'ID
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);

        // Si l'université n'existe pas, lever une exception
        if (universite == null) {
            throw new RuntimeException("L'université avec l'ID " + idUniversite + " n'a pas été trouvée.");
        }

        // Sauvegarder le foyer
        foyerRepository.save(foyer);

        // Associer et sauvegarder les blocs du foyer
        for (Bloc bloc : foyer.getBlocs()) {
            bloc.setFoyer(foyer);
            blocRepository.save(bloc);
        }

        // Associer le foyer à l'université
        universite.setFoyer(foyer);
        universiteRepository.save(universite);

        return foyer;
    }
}
