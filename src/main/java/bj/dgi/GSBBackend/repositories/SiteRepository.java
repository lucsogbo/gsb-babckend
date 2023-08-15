package bj.dgi.GSBBackend.repositories;

import bj.dgi.GSBBackend.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}
