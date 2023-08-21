package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Batiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatimentRepository extends JpaRepository<Batiment, Long> {
    List<Batiment> findAllByParcelle_Id(Long id);

}
