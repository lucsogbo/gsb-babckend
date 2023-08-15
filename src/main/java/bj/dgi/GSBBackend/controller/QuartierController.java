package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Quartier;
import bj.dgi.GSBBackend.entities.Ville;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.QuartierService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/quartier")
public class QuartierController {
    private final QuartierService quartierService;
    public QuartierController(QuartierService quartierService){this.quartierService = quartierService;}
    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de toutes les quartiers")
    public ResponseEntity<?> getAllQuartier() {
        return ResponseEntity.ok(quartierService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Quartier par id.")
    public ResponseEntity<?> getOneQuartier(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(quartierService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter un quartier .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createQuartier(@RequestBody @Valid Quartier quartier ) {

        try {

            return ResponseEntity.ok(quartierService.save(quartier));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un quartier .")
    public ResponseEntity<?> updateQuartier(@PathVariable(name = "id") Long id, @RequestBody Quartier quartier) {
        try {
            return ResponseEntity.ok(quartierService.edit(id, quartier));
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
    public Boolean deleteQuartier(@PathVariable(name = "id") Long id) {

        return quartierService.delete(id);
    }

}
