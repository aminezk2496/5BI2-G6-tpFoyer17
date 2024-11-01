package tn.esprit.tpfoyer17.Dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer17.Dao.ChambreDao;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class ChambreDaoImpl implements ChambreDao {

    private final ChambreRepository chambreRepository;

    @Override
    public List<Chambre> findAll() {
        return (List<Chambre>) chambreRepository.findAll();
    }

    @Override
    public void save(Chambre chambre) {
        chambreRepository.save(chambre);
    }

    @Override
    public Chambre findById(Long id) {
        return chambreRepository.findById(id).orElse(null);
    }

    @Override
    public List<Chambre> findByNumeroChambreIn(List<Long> numeroChambreList) {
        return chambreRepository.findByNumeroChambreIn(numeroChambreList);
    }

    @Override
    public List<Chambre> findByBlocIdBloc(Long idBloc) {
        return chambreRepository.findByBlocIdBloc(idBloc);
    }

    @Override
    public List<Chambre> findByTypeChambreAndReservationsEstValide(TypeChambre typeChambre, boolean estvalide) {
        return chambreRepository.findByTypeChambreAndReservationsEstValide(typeChambre, estvalide);
    }

    @Override
    public List<Chambre> findByBlocFoyerCapaciteFoyerGreaterThan(long value) {
        return chambreRepository.findByBlocFoyerCapaciteFoyerGreaterThan(value);
    }

    @Override
    public Chambre findByReservationsIdReservation(String reservationsIdReservation) {
        return chambreRepository.findByReservationsIdReservation(reservationsIdReservation);
    }

    @Override
    public List<Chambre> findByBlocFoyerUniversiteNomUniversiteLike(String nomUniversite) {
        return chambreRepository.findByBlocFoyerUniversiteNomUniversiteLike(nomUniversite);
    }

    @Override
    public List<Chambre> findByBlocIdBlocAndTypeChambre(long idBloc, TypeChambre typeChambre) {
        return chambreRepository.findByBlocIdBlocAndTypeChambre(idBloc, typeChambre);
    }

    @Override
    public List<Chambre> recupererParBlocEtTypeChambre(long idBloc, TypeChambre typeChambre) {
        return chambreRepository.recupererParBlocEtTypeChambre(idBloc, typeChambre);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre typeChambre) {
        return chambreRepository.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre);
    }

    @Override
    public List<Chambre> getChambresNonReserve() {
        return chambreRepository.getChambresNonReserve();
    }
}
