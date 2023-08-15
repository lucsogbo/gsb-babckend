package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Recharge;
import bj.dgi.GSBBackend.repositories.RechargeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RechargeService {
    private final RechargeRepository rechargeRepository;
    public RechargeService(RechargeRepository rechargeRepository){this.rechargeRepository = rechargeRepository;}
    public Recharge save(Recharge recharge){return rechargeRepository.save(recharge);}
    public Recharge edit(Long id, Recharge recharge){
        Recharge entite = rechargeRepository.getById(id);
        if(entite!=null){
            entite.setNumero(recharge.getNumero());
            entite.setDate(recharge.getDate());
            entite.setMontant(recharge.getMontant());
            entite.setCompteur(recharge.getCompteur());
            return rechargeRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(rechargeRepository.existsById(id)==true) rechargeRepository.deleteById(id);
        return !rechargeRepository.existsById(id);
    }
    public Optional<Recharge> getById(Long id){
        return rechargeRepository.findById(id);
    }
    public List<Recharge> getAll(){
        return rechargeRepository.findAll();
    }
}
