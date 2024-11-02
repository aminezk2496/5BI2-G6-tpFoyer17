package tn.esprit.tpfoyer17.Dao;

import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.util.List;

public interface ChambreDao {
    List<Chambre> findAll();
    void save(Chambre chambre);
    Chambre findById(Long id);
    List<Chambre> findByNumeroChambreIn(List<Long> numeroChambreList);
    List<Chambre> findByBlocIdBloc(Long idBloc);
    List<Chambre> findByTypeChambreAndReservationsEstValide(TypeChambre typeChambre, boolean estvalide);
    List<Chambre> findByBlocFoyerCapaciteFoyerGreaterThan(long value);
    Chambre findByReservationsIdReservation(String reservationsIdReservation);
    List<Chambre> findByBlocFoyerUniversiteNomUniversiteLike(String nomUniversite);
    List<Chambre> findByBlocIdBlocAndTypeChambre(long idBloc, TypeChambre typeChambre);
    List<Chambre> recupererParBlocEtTypeChambre(long idBloc, TypeChambre typeChambre);
    List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre typeChambre);
    List<Chambre> getChambresNonReserve();
}