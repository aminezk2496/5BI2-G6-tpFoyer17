package tn.esprit.tpfoyer17.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer17.entities.Etudiant;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EtudiantRepository extends CrudRepository<Etudiant,Long> {
//List<Etudiant> findByReservationsAnneeUniversitaire(LocalDate date);
Etudiant findByCinEtudiant(long cin);

    @Query("SELECT e FROM Etudiant e JOIN e.reservations r WHERE YEAR(r.anneeUniversitaire) = :year")
    List<Etudiant> findByReservationsAnneeUniversitaire(@Param("year") int year);
}
