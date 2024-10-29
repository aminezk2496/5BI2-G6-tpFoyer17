
package tn.esprit.tpfoyer17.services.impementations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.BlocNotFoundException;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.interfaces.IBlocService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlocService implements IBlocService {
    BlocRepository blocRepository;

    @Override
    public List<Bloc> retrieveBlocs() {
        return (List<Bloc>) blocRepository.findAll();
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        if (bloc.getIdBloc() == null || !blocRepository.existsById(bloc.getIdBloc())) {
            throw new RuntimeException("Bloc not found with id: " + bloc.getIdBloc());
        }
        if (bloc.getNomBloc() == null || bloc.getNomBloc().isEmpty()) {
            throw new IllegalArgumentException("Bloc name cannot be null or empty");
        }
        return blocRepository.save(bloc);
    }



    @Override
    public Bloc addBloc(Bloc bloc) {
        if (bloc.getNomBloc() == null) {
            throw new IllegalArgumentException("Bloc name cannot be null");
        }
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc retrieveBloc(long idBloc) {
        return blocRepository.findById(idBloc).orElse(null);
    }

    @Override
    public void removeBloc(long idBloc) {
        if (!blocRepository.existsById(idBloc)) {
            throw new RuntimeException("Bloc not found");
        }
        blocRepository.deleteById(idBloc);
    }

    @Override
    public List<Bloc> findByFoyerIdFoyer(long idFoyer) {
        return blocRepository.findByFoyerIdFoyer(idFoyer);
    }

    @Override
    public Bloc findByChambresIdChambre(Long idChambre) {
        return blocRepository.findByChambresIdChambre(idChambre);
    }

    @Override
    public Bloc findBlocById(long id) {
        return blocRepository.findById(id)
                .orElseThrow(() -> new BlocNotFoundException("Bloc not found with id: " + id));
    }



}
