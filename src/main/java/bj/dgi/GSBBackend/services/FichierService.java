package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Fichier;
import bj.dgi.GSBBackend.repositories.FichierRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class FichierService {

    private final FichierRepository fichierRepository;


    public FichierService(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;

    }


    //************//
    public Fichier save(Fichier fichier){
//        Date aujourdhui = new Date();
//
//        DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
//        fichier.setDate_save(shortDateFormat.format(aujourdhui));
        return fichierRepository.save(fichier);}
    public Fichier edit(Long id,Fichier fichier){
        Fichier entite = fichierRepository.getById(id);
        if(entite!=null){

            return fichierRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(fichierRepository.existsById(id)==true) fichierRepository.deleteById(id);
        return !fichierRepository.existsById(id);
    }
    public Optional<Fichier> getById(Long id){
        return fichierRepository.findById(id);
    }
    public Fichier getByid(Long id){return fichierRepository.getById(id);}
    public List<Fichier> getAll(){
        return fichierRepository.findAll();
    }

}
