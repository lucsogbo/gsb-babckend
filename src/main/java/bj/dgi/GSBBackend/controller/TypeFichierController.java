package bj.dgi.GSBBackend.controller;


import bj.dgi.GSBBackend.entities.TypeFichier;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.TypeFichierService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/typeFichier")
public class TypeFichierController {
    private final TypeFichierService typeFichierService;
    public TypeFichierController(TypeFichierService typeFichierService){this.typeFichierService = typeFichierService;}
    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de toutes les typeFichiers")
    public ResponseEntity<?> getAllTypeFichier() {
        return ResponseEntity.ok(typeFichierService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "TypeFichier par id.")
    public ResponseEntity<?> getOneTypeFichier(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(typeFichierService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter un typeFichier .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createTypeFichier(@RequestBody @Valid TypeFichier typeFichier ) {

        try {

            return ResponseEntity.ok(typeFichierService.save(typeFichier));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un typeFichier .")
    public ResponseEntity<?> updateTypeFichier(@PathVariable(name = "id") Long id, @RequestBody TypeFichier typeFichier) {
        try {
            return ResponseEntity.ok(typeFichierService.edit(id, typeFichier));
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
    public Boolean deleteTypeFichier(@PathVariable(name = "id") Long id) {

        return typeFichierService.delete(id);
    }

}

