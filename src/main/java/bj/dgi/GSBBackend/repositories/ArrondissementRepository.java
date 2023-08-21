package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Arrondissement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArrondissementRepository extends JpaRepository<Arrondissement, Long> {
    List<Arrondissement> findAllByCommune_Id(Long id);
}
