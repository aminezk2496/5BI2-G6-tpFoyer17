package tn.esprit.tpfoyer17.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.services.interfaces.IFoyerService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/foyers")
@CrossOrigin(origins = "http://localhost:4200")
public class FoyerController {
    @GetMapping("/retrieveAllFoyers")
    public List<Foyer> retrieveAllFoyers() {
        return foyerService.retrieveAllFoyers();
    }
@PostMapping("/addFoyer")
    public Foyer addFoyer(@RequestBody Foyer f) {
        return foyerService.addFoyer(f);
    }
@PutMapping("/updateFoyer")
    public Foyer updateFoyer( @RequestBody Foyer f) {
        return foyerService.updateFoyer(f);
    }
@GetMapping("/retrieveFoyer/{idFoyer}")
    public Foyer retrieveFoyer(@PathVariable("idFoyer") long idFoyer) {
        return foyerService.retrieveFoyer(idFoyer);
    }
@DeleteMapping("/removeFoyer/{idFoyer}")
    public void removeFoyer(@PathVariable("idFoyer") long idFoyer) {
        foyerService.removeFoyer(idFoyer);
    }
    @PutMapping("/ajouterFoyerEtAffecterAUniversite/{idUniversite}")
    public ResponseEntity<Foyer> ajouterFoyerEtAffecterAUniversite(@RequestBody Foyer foyer, @PathVariable("idUniversite") long idUniversite) {
        try {
            Foyer savedFoyer = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);
            return ResponseEntity.ok(savedFoyer);  // Retourne le foyer ajouté avec un code 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Retourne une erreur 500 en cas de problème
        }
    }


    IFoyerService foyerService;
}
