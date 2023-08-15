package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Ville;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.VilleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/ville")
public class VilleController {
    private final VilleService villeService;
    public VilleController(VilleService villeService){this.villeService = villeService;}

    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de toutes les villes")
    public ResponseEntity<?> getAllVille() {
        return ResponseEntity.ok(villeService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Ville par id.")
    public ResponseEntity<?> getOneVille(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(villeService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter une ville .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createVille(@RequestBody @Valid Ville ville ) {

        try {

            return ResponseEntity.ok(villeService.save(ville));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  une ville .")
    public ResponseEntity<?> updateVille(@PathVariable(name = "id") Long id, @RequestBody Ville ville) {
        try {
            return ResponseEntity.ok(villeService.edit(id, ville));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer ville par id")
    public Boolean deleteVille(@PathVariable(name = "id") Long id) {

        return villeService.delete(id);
    }
}
