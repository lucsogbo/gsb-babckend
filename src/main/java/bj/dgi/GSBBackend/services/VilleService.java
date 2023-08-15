package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Ville;
import bj.dgi.GSBBackend.repositories.VilleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {
    private final VilleRepository villeRepository;
    public VilleService(VilleRepository villeRepository){this.villeRepository = villeRepository;}
    public Ville save(Ville ville){return villeRepository.save(ville);}
    public Ville edit(Long id, Ville ville){
        Ville entite = villeRepository.getById(id);
        if(entite!=null){
            entite.setDepartement(ville.getDepartement());
            entite.setNom(ville.getNom());
            return villeRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(villeRepository.existsById(id)==true) villeRepository.deleteById(id);
        return !villeRepository.existsById(id);
    }
    public Optional<Ville> getById(Long id){
        return villeRepository.findById(id);
    }
    public List<Ville> getAll(){
        return villeRepository.findAll();
    }
}
