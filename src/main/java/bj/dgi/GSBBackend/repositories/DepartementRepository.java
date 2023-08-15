package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
