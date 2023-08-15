package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Batiment;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.BatimentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/sites/batiment")
@CrossOrigin
public class BatimentController {
    private final BatimentService batimentService;
    public BatimentController(BatimentService batimentService){this.batimentService = batimentService;}

    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes de tous les batiments")
    public ResponseEntity<?> getAllBatiment() {
        return ResponseEntity.ok(batimentService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Batiment par id.")
    public ResponseEntity<?> getOneBatiment(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(batimentService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter une batiment .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createBatiment(@RequestBody @Valid Batiment batiment) {

        try {

            return ResponseEntity.ok(batimentService.save(batiment));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un batiment .")
    public ResponseEntity<?> updateBatiment(@PathVariable(name = "id") Long id, @RequestBody Batiment batiment) {
        try {
            return ResponseEntity.ok(batimentService.edit(id, batiment));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer batiment par id")
    public Boolean deleteBatiment(@PathVariable(name = "id") Long id) {

        return batimentService.delete(id);
    }
}
