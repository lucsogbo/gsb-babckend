package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Commune;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.CommuneService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/commune")
public class CommuneController {
    private final CommuneService communeService;
    public CommuneController(CommuneService communeService){this.communeService = communeService;}
    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de toutes les communes")
    public ResponseEntity<?> getAllCommune() {
        return ResponseEntity.ok(communeService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Commune par id.")
    public ResponseEntity<?> getOneCommune(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(communeService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter un commune .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createCommune(@RequestBody @Valid Commune commune ) {

        try {

            return ResponseEntity.ok(communeService.save(commune));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un commune .")
    public ResponseEntity<?> updateCommune(@PathVariable(name = "id") Long id, @RequestBody Commune commune) {
        try {
            return ResponseEntity.ok(communeService.edit(id, commune));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer Commune par id")
    public Boolean deleteCommune(@PathVariable(name = "id") Long id) {

        return communeService.delete(id);
    }

}