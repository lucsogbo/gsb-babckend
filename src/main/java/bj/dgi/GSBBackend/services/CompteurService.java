package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.C_electricite;
import bj.dgi.GSBBackend.entities.Compteur;
import bj.dgi.GSBBackend.repositories.CompteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompteurService {
    private final CompteurRepository compteurRepository;
    public CompteurService(CompteurRepository compteurRepository){
        this.compteurRepository=compteurRepository;
    }
    public Compteur save(Compteur compteur){
        return compteurRepository.save(compteur);
    }
    public Compteur edit(Long id,Compteur compteur){
        Compteur entite = compteurRepository.getById(id);
        if(entite!=null){
            entite.setAbonne(compteur.getAbonne());
            entite.setNumero_compteur(compteur.getNumero_compteur());
            entite.setDate_attribution(compteur.getDate_attribution());
            entite.setImpaye(compteur.isImpaye());
            entite.setObservation(compteur.getObservation());
            entite.setNumero_police(compteur.getNumero_police());

            return compteurRepository.save(entite);
        }
        return  null;
    }
    public boolean delete(Long id){
        if(compteurRepository.existsById(id)==true)
            compteurRepository.deleteById(id);
        return !compteurRepository.existsById(id);
    }
    public Optional<Compteur> getById(Long id){
        return compteurRepository.findById(id);
    }
    public List<Compteur> getAll(){
        return compteurRepository.findAll();
    }
}
