package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
    List<Commune> findAllByDepartement_Id(Long id);
}
