package tn.esprit.tpfoyer17.services.interfaces;

import tn.esprit.tpfoyer17.entities.Bloc;

import java.util.List;

public interface IBlocService {
    List<Bloc> retrieveBlocs();
    void updateBloc(Long id, Bloc newBloc);  // Ensure this matches the implementation
    Bloc addBloc(Bloc bloc);
    Bloc retrieveBloc(long idBloc);
    void removeBloc(long idBloc);
    List<Bloc> findByFoyerIdFoyer(long idFoyer);
    Bloc findByChambresIdChambre(Long idChambre);
}
