package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.Departement;
import bj.dgi.GSBBackend.entities.Fichier;
import bj.dgi.GSBBackend.payload.UploadFileResponse;
import bj.dgi.GSBBackend.services.FichierService;
import bj.dgi.GSBBackend.services.FileStorageService;
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
import java.io.IOException;
import java.util.Date;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/fichier")
public class FichierController {
    private final FichierService fichierService;
    private static final Logger logger = LoggerFactory.getLogger(FichierController.class);
    @Autowired
    private FileStorageService fileStorageService;

    public FichierController(FichierService fichierService) {
        this.fichierService = fichierService;

    }

    @PostMapping("/create")
    @ApiOperation(value = "Enregistrer un fichier")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Fichier fichier = new Fichier();
        fichier.setNomFichier(fileName);
       // fichier.setDate_save(date);
        fichier.setType(file.getContentType());
        fichierService.save(fichier);
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {
        // Load file as Resource

        Fichier fichier = fichierService.getByid(id);
        String fileName = fichier.getNomFichier();
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


}
