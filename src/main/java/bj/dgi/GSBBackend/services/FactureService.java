package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Facture;
import bj.dgi.GSBBackend.repositories.FactureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureService {
    private final FactureRepository factureRepository;
    public FactureService(FactureRepository factureRepository){this.factureRepository=factureRepository;}
    public Facture save(Facture facture){return factureRepository.save(facture);}

    public Facture edit(Long id,Facture facture) {
        Facture entite = factureRepository.getById(id);
        if(entite!=null){
            entite.setNumero(facture.getNumero());
            entite.setDebut_periode(facture.getDebut_periode());
            entite.setFin_priode(facture.getFin_priode());
            entite.setEcheance_paiement(facture.getEcheance_paiement());
            entite.setMontant(facture.getMontant());
            entite.setPaye(facture.isPaye());
            entite.setCompteur(facture.getCompteur());
            return factureRepository.save(entite);
        }
         return null;
    }
    public boolean delete(Long id){
        if(factureRepository.existsById(id)==true) factureRepository.deleteById(id);
        return !factureRepository.existsById(id);
    }
    public Optional<Facture> getById(Long id){
        return factureRepository.findById(id);
    }
    public List<Facture> getAll(){ return factureRepository.findAll(); }
}
