package tn.esprit.tpfoyer17.services.impementations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.Dao.ChambreDao; // Import de ChambreDao
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
    ChambreDao chambreDao; // Injection de ChambreDao
    BlocRepository blocRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        return chambreDao.findAll(); // Utilisation du DAO
    }

    @Override
    public Chambre addChambre(Chambre c) {
        chambreDao.save(c); // Utilisation du DAO
        return c; // Retourner l'objet après l'avoir sauvegardé
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        chambreDao.save(c); // Utilisation du DAO
        return c; // Retourner l'objet après la mise à jour
    }

    @Override
    public Chambre retrieveChambre(long idChambre) {
        return chambreDao.findById(idChambre); // Utilisation du DAO
    }

    @Override
    public List<Chambre> findByTypeChambre() {
        return chambreDao.findByTypeChambreAndReservationsEstValide(TypeChambre.DOUBLE, true); // Utilisation du DAO
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);
        List<Chambre> chambreList = chambreDao.findByNumeroChambreIn(numChambre); // Utilisation du DAO

        for(Chambre chambre: chambreList) {
            chambre.setBloc(bloc);
            chambreDao.save(chambre); // Utilisation du DAO
        }
        return bloc;
    }

    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        return chambreDao.findByBlocFoyerUniversiteNomUniversiteLike(nomUniversite); // Utilisation du DAO
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        return chambreDao.findByBlocIdBlocAndTypeChambre(idBloc, typeC); // Utilisation du DAO
    }

    @Override
    public List<Chambre> getChambresParBlocEtTypeJPQL(long idBloc, TypeChambre typeC) {
        return chambreDao.recupererParBlocEtTypeChambre(idBloc, typeC); // Utilisation du DAO
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        return chambreDao.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, type); // Utilisation du DAO
    }

    @Scheduled(cron = "*/10 * * * * *")
    @Override
    public void getChambresNonReserve() {
        for (Chambre chambre : chambreDao.getChambresNonReserve()) { // Utilisation du DAO
            log.info(chambre.toString());
        }
    }
}
