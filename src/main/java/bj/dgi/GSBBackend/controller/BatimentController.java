package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Batiment;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.repositories.BatimentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/sites/batiment")
@CrossOrigin
public class BatimentController {
    private final BatimentRepository batimentRepository;
    public BatimentController(BatimentRepository batimentRepository){this.batimentRepository = batimentRepository;}

    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes de tous les batiments")
    public ResponseEntity<?> getAllBatiment() {
        return ResponseEntity.ok(batimentRepository.findAll());
    }

    @GetMapping(path = "/all-batiments-for-parcelle/{id_parcelle}")
    @ApiOperation(value = "Listes de tous les batiments")
    public ResponseEntity<?> getAllBatiment(@PathVariable(value = "id_parcelle") Long id_parcelle) {
        return ResponseEntity.ok(batimentRepository.findAllByParcelle_Id(id_parcelle));
    }
    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Batiment par id.")
    public ResponseEntity<?> getOneBatiment(@PathVariable(value = "id") Long id) {
        try {
            if (batimentRepository.existsById(id)){
                return ResponseEntity.ok(batimentRepository.findById(id));
            }
            return ResponseEntity.ok("Batiment introuvable");

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

            return ResponseEntity.ok(batimentRepository.save(batiment));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping(path = "/update")
    @ApiOperation(value = " Modifier  un batiment .")
    public ResponseEntity<?> updateBatiment(@RequestBody Batiment batiment) {
        try {
                Optional<Batiment> batimentOp = batimentRepository.findById(batiment.getId());
                if (batimentOp.isPresent()){
                    batimentOp.get().setType_batiment(batiment.getType_batiment());
                    batimentOp.get().setLoyer(batiment.getLoyer());
                    batimentOp.get().setSituation_juridique(batiment.getSituation_juridique());
                    batimentOp.get().setEtat(batiment.getEtat());
                    batimentOp.get().setDate_debut_traveau(batiment.getDate_debut_traveau());
                    batimentOp.get().setDate_fin_traveau(batiment.getDate_fin_traveau());
                    batimentOp.get().setObservation(batiment.getObservation());
                    batimentOp.get().setMise_en_service(batiment.getMise_en_service());
                    batimentOp.get().setReception(batiment.getReception());
                    return ResponseEntity.ok(batimentRepository.save(batimentOp.get()));
            }
            return ResponseEntity.ok("Batiment introuvable");
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer Batiment par id")
    public ResponseEntity<?>  deleteBatiment(@RequestBody Batiment batiment) {
        if (batimentRepository.existsById(batiment.getId())){
            batimentRepository.delete(batiment);
            return ResponseEntity.ok("Batiment supprimé avec succè");
        }
        return ResponseEntity.ok("Batiment introubvable");
    }
}
