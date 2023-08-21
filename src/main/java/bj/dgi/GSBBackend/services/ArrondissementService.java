package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Arrondissement;
import bj.dgi.GSBBackend.repositories.ArrondissementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArrondissementService {
    private final ArrondissementRepository arrondissementRepository;
    public ArrondissementService(ArrondissementRepository arrondissementRepository){
        this.arrondissementRepository = arrondissementRepository;}
    public Arrondissement save(Arrondissement arrondissement){return arrondissementRepository.save(arrondissement);}
    public Arrondissement edit(Long id, Arrondissement arrondissement){
        Arrondissement entite = arrondissementRepository.getById(id);
        if(entite!=null){
            entite.setNom(arrondissement.getNom());
            return arrondissementRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(arrondissementRepository.existsById(id)==true) arrondissementRepository.deleteById(id);
        return !arrondissementRepository.existsById(id);
    }
    public Optional<Arrondissement> getById(Long id){
        return arrondissementRepository.findById(id);
    }
    public List<Arrondissement> getAll(){
        return arrondissementRepository.findAll();
    }
}
