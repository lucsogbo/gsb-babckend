package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Departement;
import bj.dgi.GSBBackend.repositories.DepartementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {
    private final DepartementRepository departementRepository;
    public DepartementService(DepartementRepository departementRepository){
        this.departementRepository = departementRepository;}
    public Departement save(Departement departement){return departementRepository.save(departement);}
    public Departement edit(Long id,Departement departement){
       Departement entite = departementRepository.getById(id);
       if(entite!=null){
           entite.setNom(departement.getNom());
           return departementRepository.save(entite);
       }
       return null;
    }
    public boolean delete(Long id){
        if(departementRepository.existsById(id)==true) departementRepository.deleteById(id);
        return !departementRepository.existsById(id);
    }
    public Optional<Departement> getById(Long id){
        return departementRepository.findById(id);
    }
    public List<Departement> getAll(){
        return departementRepository.findAll();
    }
}
