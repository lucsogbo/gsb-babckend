package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Fichier;
import bj.dgi.GSBBackend.repositories.BatimentRepository;
import bj.dgi.GSBBackend.repositories.FactureRepository;
import bj.dgi.GSBBackend.repositories.FichierRepository;
import bj.dgi.GSBBackend.repositories.ParcelleRepository;
import bj.dgi.GSBBackend.services.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;


@RestController

@CrossOrigin

@RequestMapping(path = "/api/fichier")
public class FichierController {
    private final FichierRepository fichierRepository;
    private final FactureRepository factureRepository;
    private final BatimentRepository batimentRepository;
    private final ParcelleRepository parcelleRepository;
    private final TypeFichierService typeFichierService;
    private static final Logger logger = LoggerFactory.getLogger(FichierController.class);
    @Autowired
    private FileStorageService fileStorageService;
    public FichierController(FichierRepository fichierRepository, FactureRepository factureRepository
            , BatimentRepository batimentRepository, ParcelleRepository parcelleRepository,
                             TypeFichierService typeFichierService) {
        this.fichierRepository = fichierRepository;
        this.factureRepository = factureRepository;
        this.batimentRepository = batimentRepository;
        this.parcelleRepository = parcelleRepository;
        this.typeFichierService = typeFichierService;
    }

    @Transactional
    @PostMapping("/create/{parent}/{id_parent}/{id_typeFichier}")
    @ApiOperation(value = "Enregistrer un fichier")
    public ResponseEntity<?> upload_File(@PathVariable(value = "parent") char parent, @PathVariable(value = "id_parent") Long id_parent, @PathVariable(value = "id_typeFichier") Long id_typeFichier, @RequestBody MultipartFile file) {
        Fichier fichier = new Fichier();
        if((parent=='F')&&factureRepository.findById(id_parent).isPresent()){
            fichier.setFacture(factureRepository.findById(id_parent).get());
        }
        if ((parent=='P')&&parcelleRepository.findById(id_parent).isPresent()){
            fichier.setParcelle(parcelleRepository.findById(id_parent).get());
        }
        if ((parent=='B')&&batimentRepository.findById(id_parent).isPresent()){
            fichier.setBatiment(batimentRepository.findById(id_parent).get());
        }
        String fileName =file.getOriginalFilename();
        fichier.setTypeFichier(typeFichierService.getById_TF(id_typeFichier));
        fichier.setRef_piece(fileName);
        Fichier fichierStored = fichierRepository.save(fichier);

        fileStorageService.storeFile(file,fichierStored.getId());
        fichierStored.setRef_piece(fichier.getId()+"_"+fileName);
        fichierStored.setUrlDownload("/fichier/downloadFile/"+fichierStored.getId());
        fichierRepository.save(fichierStored);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


//         fichier.setDate_save(date);
//        fichier.setType(file.getContentType());
        //fichierService.save(fichier);
//        return new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
        return ResponseEntity.ok("Le fichier a été ajouté avec succè!");
    }


    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {
        // Load file as Resource
        if (!fichierRepository.findById(id).isPresent()){
            logger.info("Fichier introuvable.");
            return null;
        }
        Fichier fichier = fichierRepository.findById(id).get();
        String fileName = fichier.getRef_piece();
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        //Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @GetMapping(path = "/all-files-for-batiment/{id_batiment}")
    @ApiOperation(value = "Listes de tous les fichiers d'un batiment")
    public ResponseEntity<?> getAllBatiment(@PathVariable(value = "id_batiment") Long id_batiment) {
        return ResponseEntity.ok(fichierRepository.findAllByBatiment_Id(id_batiment));
    }
    @GetMapping(path = "/all-files-for-facture/{id_facture}")
    @ApiOperation(value = "Listes de tous les fichiers d'un facture")
    public ResponseEntity<?> getAllFacture(@PathVariable(value = "id_facture") Long id_facture) {
        return ResponseEntity.ok(fichierRepository.findAllByFacture_Id(id_facture));
    }
    @GetMapping(path = "/all-files-for-parcelle/{id_parcelle}")
    @ApiOperation(value = "Listes de tous les fichiers d'un parcelle")
    public ResponseEntity<?> getAllParcelle(@PathVariable(value = "id_parcelle") Long id_parcelle) {
        return ResponseEntity.ok(fichierRepository.findAllByParcelle_Id(id_parcelle));
    }

}
