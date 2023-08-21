package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.TypeFichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeFichier, Long> {
}
