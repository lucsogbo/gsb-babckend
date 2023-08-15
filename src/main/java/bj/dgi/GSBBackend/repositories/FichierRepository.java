package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Facture;
import bj.dgi.GSBBackend.entities.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichierRepository extends JpaRepository<Fichier, Long> {

}
