package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Quartier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartierRepository extends JpaRepository<Quartier, Long> {
    List<Quartier> findAllByArrondissement_Id(Long id);
}
