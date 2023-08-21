package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Arrondissement;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.ArrondissementService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/arrondissement")
public class ArrondissementController {
    private final ArrondissementService arrondissementService;
    public ArrondissementController(ArrondissementService arrondissementService){this.arrondissementService = arrondissementService;}
    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de toutes les arrondissements")
    public ResponseEntity<?> getAllArrondissement() {
        return ResponseEntity.ok(arrondissementService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Arrondissement par id.")
    public ResponseEntity<?> getOneArrondissement(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(arrondissementService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter un arrondissement .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createArrondissement(@RequestBody @Valid Arrondissement arrondissement ) {

        try {

            return ResponseEntity.ok(arrondissementService.save(arrondissement));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un arrondissement .")
    public ResponseEntity<?> updateArrondissement(@PathVariable(name = "id") Long id, @RequestBody Arrondissement arrondissement) {
        try {
            return ResponseEntity.ok(arrondissementService.edit(id, arrondissement));
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
    public Boolean deleteArrondissement(@PathVariable(name = "id") Long id) {

        return arrondissementService.delete(id);
    }

}