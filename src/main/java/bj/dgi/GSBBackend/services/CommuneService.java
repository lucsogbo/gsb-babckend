package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Commune;
import bj.dgi.GSBBackend.repositories.CommuneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommuneService {
    private final CommuneRepository communeRepository;
    public CommuneService(CommuneRepository communeRepository){
        this.communeRepository = communeRepository;}
    public Commune save(Commune commune){return communeRepository.save(commune);}
    public Commune edit(Long id,Commune commune){
        Commune entite = communeRepository.getById(id);
        if(entite!=null){
            entite.setNom(commune.getNom());
            return communeRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(communeRepository.existsById(id)==true) communeRepository.deleteById(id);
        return !communeRepository.existsById(id);
    }
    public Optional<Commune> getById(Long id){
        return communeRepository.findById(id);
    }
    public List<Commune> getAll(){
        return communeRepository.findAll();
    }
}
