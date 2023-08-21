package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.TypeFichier;
import bj.dgi.GSBBackend.repositories.TypeFichierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeFichierService {
    private final TypeFichierRepository typeFichierRepository;
    public TypeFichierService(TypeFichierRepository typeFichierRepository){
        this.typeFichierRepository = typeFichierRepository;}
    public TypeFichier save(TypeFichier typeFichier){return typeFichierRepository.save(typeFichier);}
    public TypeFichier edit(Long id,TypeFichier typeFichier){
        TypeFichier entite = typeFichierRepository.getById(id);
        if(entite!=null){
            entite.setNom(typeFichier.getNom());
            return typeFichierRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(typeFichierRepository.existsById(id)==true) typeFichierRepository.deleteById(id);
        return !typeFichierRepository.existsById(id);
    }
    public Optional<TypeFichier> getById(Long id){
        return typeFichierRepository.findById(id);
    }
    public TypeFichier getById_TF(Long id){return typeFichierRepository.getById(id);}
    public List<TypeFichier> getAll(){
        return typeFichierRepository.findAll();
    }
}
