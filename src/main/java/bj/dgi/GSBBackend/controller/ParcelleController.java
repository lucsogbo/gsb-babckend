package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Parcelle;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.repositories.ParcelleRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import java.util.Optional;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/parcelle")
public class ParcelleController {
    private final ParcelleRepository parcelleRepository;
    public ParcelleController(ParcelleRepository parcelleRepository){this.parcelleRepository = parcelleRepository;}

    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes de toutes les Parcelles")
    public ResponseEntity<?> getAllParcelle() {

        return ResponseEntity.ok(parcelleRepository.findAll());
    }
    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Parcelle par id.")
    public ResponseEntity<?> getOneParcelle(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(parcelleRepository.findById(id));
        } catch (Exception e) {
            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    @ApiOperation(value = " ajouter une Parcelle .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createParcelle(@RequestBody @Valid Parcelle parcelle) {
        try {
            return ResponseEntity.ok(parcelleRepository.save(parcelle));
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/update")
    @ApiOperation(value = " Modifier  un Parcelle .")
    public ResponseEntity<?> updateParcelle(@RequestBody Parcelle parcelle) {
        try {
            Optional <Parcelle> parcelleOp = parcelleRepository.findById(parcelle.getId());
            if (parcelleOp.isPresent()){
                parcelleOp.get().setReference(parcelle.getReference());
                parcelleOp.get().setBati(parcelle.isBati());
                parcelleOp.get().setLouer(parcelle.isLouer());
                parcelleOp.get().setDon(parcelle.isDon());
                parcelleOp.get().setLevee_topographique(parcelle.isLevee_topographique());
                parcelleOp.get().setSituation_juridique(parcelle.getSituation_juridique());
                parcelleOp.get().setSituation_geographique(parcelle.getSituation_geographique());
                parcelleOp.get().setSuperficie(parcelle.getSuperficie());
                parcelleOp.get().setQuartier(parcelle.getQuartier());
                parcelleOp.get().setSuperficie(parcelle.getSuperficie());
                parcelleOp.get().setReference_acte_donation(parcelle.getReference_acte_donation());
                parcelleOp.get().setReference_convention_vente(parcelle.getReference_convention_vente());
                parcelleOp.get().setReference_titre_foncier(parcelle.getReference_titre_foncier());
                parcelleOp.get().setStructure(parcelle.getStructure());
                return ResponseEntity.ok(parcelleRepository.save(parcelleOp.get()));
            }
            return ResponseEntity.ok("Parcelle introuvable");
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    @ApiOperation(value = " Supprimer Parcelle par id")
    public ResponseEntity<?>  deleteParcelle(@RequestBody Parcelle parcelle) {
        if (parcelleRepository.existsById(parcelle.getId())){
            parcelleRepository.delete(parcelle);
            return ResponseEntity.ok("Parcelle supprimé avec succè");
        }
        return ResponseEntity.ok("Parcelle introubvable");
    }
    @GetMapping(path = "/list_paginer")
    @ApiOperation(value = " liste paginée des parcelles")
    public ResponseEntity<?> findAllPageable(Pageable pageable) {
        LOGGER.debug("GET ALL FROM OUR ENTITY PAGINATED"); // simple logging
       // ADD SORTING
        Pageable SortedByIdAsc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").ascending());
        return  ResponseEntity.ok(parcelleRepository.findAll(SortedByIdAsc));
    }
}
