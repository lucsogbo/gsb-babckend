package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Quartier;
import bj.dgi.GSBBackend.repositories.QuartierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartierService {
    private final QuartierRepository quartierRepository;
    public QuartierService(QuartierRepository quartierRepository){this.quartierRepository = quartierRepository;}
    public Quartier save(Quartier quartier){return quartierRepository.save(quartier);}
    public Quartier edit(Long id, Quartier quartier){
        Quartier entite = quartierRepository.getById(id);
        if(entite!=null){
            entite.setNom(quartier.getNom());
            entite.setVille(quartier.getVille());
            entite.setSituation_geographique(quartier.getSituation_geographique());
            return quartierRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(quartierRepository.existsById(id)==true){
            quartierRepository.deleteById(id);
        }
        return !quartierRepository.existsById(id);
    }
    public Optional<Quartier> getById(Long id){
        return quartierRepository.findById(id);
    }
    public List<Quartier> getAll(){
        return quartierRepository.findAll();
    }
}
