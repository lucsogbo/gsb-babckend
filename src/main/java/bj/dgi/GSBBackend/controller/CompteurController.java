package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Batiment;
import bj.dgi.GSBBackend.entities.Compteur;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.CompteurService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/compteur")
public class CompteurController {
    private final CompteurService compteurService;

    public CompteurController(CompteurService compteurService) {
        this.compteurService = compteurService;
    }

    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes  de tous les Compteur")
    public ResponseEntity<?> getAllBatiment() {
        return ResponseEntity.ok(compteurService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Compteur par id.")
    public ResponseEntity<?> getOneBatiment(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(compteurService.getById(id));

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

            return ResponseEntity.ok(compteurService.save(compteur));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un Compteur .")
    public ResponseEntity<?> updateCompteur(@PathVariable(name = "id") Long id, @RequestBody Compteur compteur) {
        try {
            return ResponseEntity.ok(compteurService.edit(id, compteur));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer Compteurt par id")
    public Boolean deletecCompteur(@PathVariable(name = "id") Long id) {

        return compteurService.delete(id);
    }
}
