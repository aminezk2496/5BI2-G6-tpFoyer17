package tn.esprit.tpfoyer17.services.impementations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.interfaces.IBlocService;

import java.util.List;
import java.util.Optional;

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
    public Bloc updateBloc(Long id, Bloc bloc) {
        Bloc existingBloc = blocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloc not found with id: " + id));

        // Update fields
        existingBloc.setNomBloc(bloc.getNomBloc());
        existingBloc.setCapaciteBloc(bloc.getCapaciteBloc());

        return blocRepository.save(existingBloc);
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
        Bloc bloc = blocRepository.findById(idBloc)
                .orElseThrow(() -> new RuntimeException("Bloc not found"));
        bloc.getChambres().clear();  // Clear chambres to avoid foreign key constraint issues
        blocRepository.delete(bloc);
    }

    @Override
    public List<Bloc> findByFoyerIdFoyer(long idFoyer) {
        return blocRepository.findByFoyerIdFoyer(idFoyer);
    }

    @Override
    public Bloc findByChambresIdChambre(Long idChambre) {
        return blocRepository.findByChambresIdChambre(idChambre);
    }

    public Bloc findBlocById(long id) {
        Optional<Bloc> blocOptional = blocRepository.findById(id);
        return blocOptional.orElseThrow(() -> new RuntimeException("Bloc not found with id: " + id));
    }
}
