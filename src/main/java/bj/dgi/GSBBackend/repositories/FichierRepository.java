package bj.dgi.GSBBackend.repositories;


import bj.dgi.GSBBackend.entities.Fichier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FichierRepository extends JpaRepository<Fichier, Long> {
    List<Fichier> findAllByBatiment_Id(Long id);
    List<Fichier> findAllByFacture_Id(Long id);
    List<Fichier> findAllByParcelle_Id(Long id);
}
