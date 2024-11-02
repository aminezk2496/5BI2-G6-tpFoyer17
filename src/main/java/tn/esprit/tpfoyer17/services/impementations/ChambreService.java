package tn.esprit.tpfoyer17.services.impementations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.Dao.ChambreDao;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.interfaces.IChambreService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChambreService implements IChambreService {
    final ChambreDao chambreDao;
    final BlocRepository blocRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        return chambreDao.findAll();
    }

    @Override
    public Chambre addChambre(Chambre c) {
        chambreDao.save(c);
        return c;
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        chambreDao.save(c);
        return c;
    }

    @Override
    public Chambre retrieveChambre(long idChambre) {
        return chambreDao.findById(idChambre);
    }

    @Override
    public List<Chambre> findByTypeChambre() {
        return chambreDao.findByTypeChambreAndReservationsEstValide(TypeChambre.DOUBLE, true);
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);
        List<Chambre> chambreList = chambreDao.findByNumeroChambreIn(numChambre);

        for (Chambre chambre : chambreList) {
            chambre.setBloc(bloc);
            chambreDao.save(chambre);
        }
        return bloc;
    }

    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        return chambreDao.findByBlocFoyerUniversiteNomUniversiteLike(nomUniversite);
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        return chambreDao.findByBlocIdBlocAndTypeChambre(idBloc, typeC);
    }

    @Override
    public List<Chambre> getChambresParBlocEtTypeJPQL(long idBloc, TypeChambre typeC) {
        return chambreDao.recupererParBlocEtTypeChambre(idBloc, typeC);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        return chambreDao.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, type);
    }

    @Scheduled(cron = "*/10 * * * * *")
    @Override
    public void getChambresNonReserve() {
        for (Chambre chambre : chambreDao.getChambresNonReserve()) {
            log.info(chambre.toString());
        }
    }
}