package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Site;
import bj.dgi.GSBBackend.repositories.SiteRepository;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {
    private final SiteRepository siteRepository;
    public SiteService(SiteRepository siteRepository){this.siteRepository=siteRepository;}
    public Site save(Site site){return siteRepository.save(site);}
    public Site edit(Long id,Site site){
        Site entite = siteRepository.getById(id);
        if(entite!=null){
            entite.setFichier(site.getFichier());
            entite.setLevee_topographique(site.isLevee_topographique());
            entite.setNom(site.getNom());
            entite.setNumero(site.getNumero());
            entite.setNumero_convention_vente(site.getNumero_convention_vente());
            entite.setNumero_titre_foncier(site.getNumero_titre_foncier());
            entite.setQuartier(site.getQuartier());
            entite.setSuperficie(site.getSuperficie());
            entite.setNumero_titre_foncier(site.getNumero_titre_foncier());
            entite.setType_site(site.getType_site());
            entite.setTitre_foncier(site.isTitre_foncier());
            return siteRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(siteRepository.existsById(id)==true) siteRepository.deleteById(id);
        return !siteRepository.existsById(id);
    }
    public Optional<Site> getById(Long id){
        return siteRepository.findById(id);
    }
    public List<Site> getAll(){
        return siteRepository.findAll();
    }

}
