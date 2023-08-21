package bj.dgi.GSBBackend.services;

import bj.dgi.GSBBackend.entities.Structure;
import bj.dgi.GSBBackend.repositories.StructureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StructureService {
    private final StructureRepository structureRepository;
    public StructureService(StructureRepository structureRepository){
        this.structureRepository = structureRepository;}
    public Structure save(Structure structure){return structureRepository.save(structure);}
    public Structure edit(Long id,Structure structure){
        Structure entite = structureRepository.getById(id);
        if(entite!=null){
            entite.setNom(structure.getNom());
            return structureRepository.save(entite);
        }
        return null;
    }
    public boolean delete(Long id){
        if(structureRepository.existsById(id)==true) structureRepository.deleteById(id);
        return !structureRepository.existsById(id);
    }
    public Optional<Structure> getById(Long id){
        return structureRepository.findById(id);
    }
    public List<Structure> getAll(){
        return structureRepository.findAll();
    }
}
