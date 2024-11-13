package tn.esprit.tpfoyer17.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.services.interfaces.IBlocService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/blocs")
@CrossOrigin(origins = "http://192.168.33.10:4200") // Replace with the correct Angular app URL if needed
public class BlocController {

    IBlocService blocService;

    @GetMapping("/findByFoyerIdFoyer/{idFoyer}")
    public List<Bloc> findByFoyerIdFoyer(@PathVariable("idFoyer") long idFoyer) {
        log.info("Fetching blocs for foyer with ID: {}", idFoyer);
        List<Bloc> blocs = blocService.findByFoyerIdFoyer(idFoyer);
        log.info("Fetched {} blocs for foyer ID: {}", blocs.size(), idFoyer);
        return blocs;
    }

    @GetMapping("/findByChambresIdChambre/{idChambre}")
    public Bloc findByChambresIdChambre(@PathVariable("idChambre") Long idChambre) {
        log.info("Fetching bloc for chambre with ID: {}", idChambre);
        Bloc bloc = blocService.findByChambresIdChambre(idChambre);
        if (bloc == null) {
            log.warn("No bloc found for chambre with ID: {}", idChambre);
        } else {
            log.info("Found bloc with ID: {}", bloc.getIdBloc());
        }
        return bloc;
    }

    @GetMapping("/retrieveBlocs")
    public List<Bloc> retrieveBlocs() {
        log.info("Fetching all blocs...");
        List<Bloc> blocs = blocService.retrieveBlocs();
        log.info("Fetched {} blocs", blocs.size());
        return blocs;
    }

    @GetMapping("/retrieveBloc/{idBloc}")
    public Bloc retrieveBloc(@PathVariable("idBloc") long idBloc) {
        log.info("Fetching bloc with ID: {}", idBloc);
        Bloc bloc = blocService.retrieveBloc(idBloc);
        if (bloc == null) {
            log.warn("No bloc found with ID: {}", idBloc);
        } else {
            log.info("Found bloc with ID: {}", bloc.getIdBloc());
        }
        return bloc;
    }

    @PostMapping("/addBloc")
    public Bloc addBloc(@RequestBody Bloc bloc) {
        log.info("Adding a new bloc with name: {}", bloc.getNomBloc());
        Bloc addedBloc = blocService.addBloc(bloc);
        log.info("Added bloc with ID: {}", addedBloc.getIdBloc());
        return addedBloc;
    }

    @PutMapping("/updateBloc")
    public Bloc updateBloc(@RequestBody Bloc bloc) {
        log.info("Updating bloc with ID: {}", bloc.getIdBloc());
        Bloc updatedBloc = blocService.updateBloc(bloc);
        log.info("Updated bloc with ID: {}", updatedBloc.getIdBloc());
        return updatedBloc;
    }

    @DeleteMapping("/removeBloc/{idBloc}")
    public void removeBloc(@PathVariable("idBloc") long idBloc) {
        log.info("Removing bloc with ID: {}", idBloc);
        blocService.removeBloc(idBloc);
        log.info("Removed bloc with ID: {}", idBloc);
    }
}
