package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Batiment;
import bj.dgi.GSBBackend.entities.Terrain_nu;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.Terrain_nuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/Site/terrain_nu")
public class Terrain_nuController {
    private final Terrain_nuService terrain_nuService;

    public Terrain_nuController(Terrain_nuService terrain_nuService) {
        this.terrain_nuService = terrain_nuService;
    }
    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de toutes les Terrain_nus")
    public ResponseEntity<?> getAllTerrain_nu() {
        return ResponseEntity.ok(terrain_nuService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Terrain_nu par id.")
    public ResponseEntity<?> getOneTerrain_nu(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(terrain_nuService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter un Terrain_nu .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createTerrain_nu(@RequestBody @Valid Terrain_nu terrain_nu) {

        try {

            return ResponseEntity.ok(terrain_nuService.save(terrain_nu));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un Terrain_nu .")
    public ResponseEntity<?> updateTerrain_nu(@PathVariable(name = "id") Long id, @RequestBody Terrain_nu terrain_nu) {
        try {
            return ResponseEntity.ok(terrain_nuService.edit(id, terrain_nu));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer Terrain_nu par id")
    public Boolean deleteTerrain_nu(@PathVariable(name = "id") Long id) {

        return terrain_nuService.delete(id);
    }
}
