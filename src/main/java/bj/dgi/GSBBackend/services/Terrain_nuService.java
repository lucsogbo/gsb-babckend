package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Batiment;
import bj.dgi.GSBBackend.entities.Terrain_nu;
import bj.dgi.GSBBackend.repositories.BatimentRepository;
import bj.dgi.GSBBackend.repositories.Terrain_nuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class Terrain_nuService {
    private final Terrain_nuRepository terrain_nuRepository;
    public Terrain_nuService(Terrain_nuRepository terrain_nuRepository){this.terrain_nuRepository=terrain_nuRepository;}
    public Terrain_nu save(Terrain_nu terrain_nu){return terrain_nuRepository.save(terrain_nu);}
    public Terrain_nu edit(Long id,Terrain_nu terrain_nu){
        Terrain_nu entite = terrain_nuRepository.getById(id);
        if(entite!=null){

            entite.setFichier(terrain_nu.getFichier());
            entite.setLevee_topographique(terrain_nu.isLevee_topographique());
            entite.setNom(terrain_nu.getNom());
            entite.setNumero(terrain_nu.getNumero());
            entite.setNumero_convention_vente(terrain_nu.getNumero_convention_vente());
            entite.setNumero_titre_foncier(terrain_nu.getNumero_titre_foncier());
            entite.setQuartier(terrain_nu.getQuartier());
            entite.setSuperficie(terrain_nu.getSuperficie());
            entite.setNumero_titre_foncier(terrain_nu.getNumero_titre_foncier());
            entite.setType_site(terrain_nu.getType_site());
            entite.setTitre_foncier(terrain_nu.isTitre_foncier());

            entite.setNumero_acte_donation(terrain_nu.getNumero_acte_donation());



            return terrain_nuRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(terrain_nuRepository.existsById(id)) terrain_nuRepository.deleteById(id);
        return !terrain_nuRepository.existsById(id);
    }
    public Optional<Terrain_nu> getById(Long id){
        return terrain_nuRepository.findById(id);
    }
    public List<Terrain_nu> getAll(){
        return terrain_nuRepository.findAll();
    }
}
