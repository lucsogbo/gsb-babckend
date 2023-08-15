package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Batiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatimentRepository extends JpaRepository<Batiment, Long> {
}
