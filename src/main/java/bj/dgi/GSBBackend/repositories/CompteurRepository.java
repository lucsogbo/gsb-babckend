package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteurRepository extends JpaRepository<Compteur, Long> {
    List<Compteur> findAllByBatiment_Id(Long id);
}
