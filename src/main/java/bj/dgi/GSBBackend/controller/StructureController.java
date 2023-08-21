package bj.dgi.GSBBackend.controller;


import bj.dgi.GSBBackend.entities.Structure;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.StructureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/structure")
public class StructureController {
    private final StructureService structureService;
    public StructureController(StructureService structureService){this.structureService = structureService;}
    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de toutes les structures")
    public ResponseEntity<?> getAllStructure() {
        return ResponseEntity.ok(structureService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Structure par id.")
    public ResponseEntity<?> getOneStructure(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(structureService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter un structure .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createStructure(@RequestBody @Valid Structure structure ) {

        try {

            return ResponseEntity.ok(structureService.save(structure));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un structure .")
    public ResponseEntity<?> updateStructure(@PathVariable(name = "id") Long id, @RequestBody Structure structure) {
        try {
            return ResponseEntity.ok(structureService.edit(id, structure));
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
    public Boolean deleteStructure(@PathVariable(name = "id") Long id) {

        return structureService.delete(id);
    }

}
