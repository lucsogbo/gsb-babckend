package bj.dgi.GSBBackend.repositories;



import bj.dgi.GSBBackend.entities.Role;
import bj.dgi.GSBBackend.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    boolean existsByName(RoleName roleName);
}
