package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteurRepository extends JpaRepository<Compteur, Long> {
}
