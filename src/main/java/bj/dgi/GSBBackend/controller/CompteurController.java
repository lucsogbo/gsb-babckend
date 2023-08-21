package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Compteur;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.repositories.CompteurRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/compteur")
public class CompteurController {
    private final CompteurRepository compteurRepository;

    public CompteurController(CompteurRepository compteurRepository) {
        this.compteurRepository = compteurRepository;
    }

    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes  de tous les Compteur")
    public ResponseEntity<?> getAllBatiment() {
        return ResponseEntity.ok(compteurRepository.findAll());
    }

    @GetMapping(path = "/all-compteurs-for-batiemnts/{id_parcelle}")
    @ApiOperation(value = "Listes de tous les compteurs")
    public ResponseEntity<?> getAllCompteur(@PathVariable(value = "id_batiment") Long id_batiment) {
        return ResponseEntity.ok(compteurRepository.findAllByBatiment_Id(id_batiment));
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Compteur par id.")
    public ResponseEntity<?> getOneCompteur(@PathVariable(value = "id") Long id) {
        try {
            if (compteurRepository.existsById(id)){
                return ResponseEntity.ok(compteurRepository.findById(id));
            }
            return ResponseEntity.ok("Compteur introuvable");

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    @ApiOperation(value = " ajouter une Compteur .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createCompteur(@RequestBody @Valid Compteur compteur) {

        try {

            return ResponseEntity.ok(compteurRepository.save(compteur));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/update")
    @ApiOperation(value = " Modifier  un Compteur .")
    public ResponseEntity<?> updateCompteur( @RequestBody Compteur compteur) {
        try {
            Optional<Compteur> compteurOp = compteurRepository.findById(compteur.getId());
            if (compteurOp.isPresent()){
                compteurOp.get().setAbonne(compteur.getAbonne());
                compteurOp.get().setNumero_compteur(compteur.getNumero_compteur());
                compteurOp.get().setDate_attribution(compteur.getDate_attribution());
                compteurOp.get().setImpaye(compteur.isImpaye());
                compteurOp.get().setObservation(compteur.getObservation());
                compteurOp.get().setNumero_police(compteur.getNumero_police());
                return ResponseEntity.ok(compteurRepository.save(compteurOp.get()));
            }
            return ResponseEntity.ok("Compteur introuvable");
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer Compteur par id")
    public ResponseEntity<?>  deleteCompteur(@RequestBody Compteur compteur) {
        if (compteurRepository.existsById(compteur.getId())){
            compteurRepository.delete(compteur);
            return ResponseEntity.ok("Compteur supprimé avec succè");
        }
        return ResponseEntity.ok("Compteur introubvable");
    }

}
