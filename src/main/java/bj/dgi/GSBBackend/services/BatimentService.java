package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Batiment;
import bj.dgi.GSBBackend.repositories.BatimentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatimentService {
    private final BatimentRepository batimentRepository;
    public BatimentService(BatimentRepository batimentRepository){this.batimentRepository=batimentRepository;}
    public Batiment save(Batiment batiment){return batimentRepository.save(batiment);}
    public Batiment edit(Long id,Batiment batiment){
        Batiment entite = batimentRepository.getById(id);
        if(entite!=null){

            entite.setFichier(batiment.getFichier());
            entite.setLevee_topographique(batiment.isLevee_topographique());
            entite.setNom(batiment.getNom());
            entite.setNumero(batiment.getNumero());
            entite.setNumero_convention_vente(batiment.getNumero_convention_vente());
            entite.setNumero_titre_foncier(batiment.getNumero_titre_foncier());
            entite.setQuartier(batiment.getQuartier());
            entite.setSuperficie(batiment.getSuperficie());
            entite.setNumero_titre_foncier(batiment.getNumero_titre_foncier());
            entite.setType_site(batiment.getType_site());
            entite.setTitre_foncier(batiment.isTitre_foncier());

            entite.setType_batiment(batiment.getType_batiment());
            entite.setLoyer(batiment.getLoyer());
            entite.setSituation_juridique(batiment.getSituation_juridique());
            entite.setEtat(batiment.getEtat());
            entite.setDate_debut_traveau(batiment.getDate_debut_traveau());
            entite.setDate_fin_traveau(batiment.getDate_fin_traveau());
            entite.setObservation(batiment.getObservation());
            entite.setMise_en_service(batiment.getMise_en_service());
            entite.setReception(batiment.getReception());

            return batimentRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(batimentRepository.existsById(id)==true) batimentRepository.deleteById(id);
        return !batimentRepository.existsById(id);
    }
    public Optional<Batiment> getById(Long id){
        return batimentRepository.findById(id);
    }
    public List<Batiment> getAll(){
        return batimentRepository.findAll();
    }
}
