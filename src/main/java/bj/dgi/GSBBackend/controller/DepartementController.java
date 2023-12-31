package bj.dgi.GSBBackend.controller;


import bj.dgi.GSBBackend.entities.Departement;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.services.DepartementService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/departement")
public class DepartementController {

    private  final DepartementService departementService;


    public  DepartementController(DepartementService departementService){
        this.departementService = departementService;}


    @GetMapping(path = "/liste")
    @ApiOperation(value = "Listes des de tous les départements")
    public ResponseEntity<?> getAllDepartement() {
        return ResponseEntity.ok(departementService.getAll());
    }

    @GetMapping(path = "/One/{id}")
    @ApiOperation(value = "Département par id.")
    public ResponseEntity<?> getOneDepartement(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(departementService.getById(id));

        } catch (Exception e) {

            e.printStackTrace() ;
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/create")
    @ApiOperation(value = " ajouter un département .")
//	@PreAuthorize("hasRole('ROLE_CONSEILLER')")
    public ResponseEntity<?> createDepartement(@RequestBody @Valid Departement departement) {

        try {

            return ResponseEntity.ok(departementService.save(departement));

        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping(path = "/edit/{id}")
    @ApiOperation(value = " Modifier  un département .")
    public ResponseEntity<?> updateDepartement(@PathVariable(name = "id") Long id, @RequestBody Departement departement) {
        try {
            return ResponseEntity.ok(departementService.edit(id, departement));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    public Boolean deleteDepartement(@PathVariable(name = "id") Long id) {

        return departementService.delete(id);
    }
    @GetMapping(path = "/list_paginer")
    @ApiOperation(value = " liste paginé")
    public ResponseEntity<?> findAllPageable(Pageable pageable) {
        LOGGER.debug("GET ALL FROM OUR ENTITY PAGINATED"); // simple logging
        // ADD SORTING
        Pageable SortedByNomAsc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nom").ascending());
        return  ResponseEntity.ok(departementService.findAllPageable(SortedByNomAsc));
    }
}