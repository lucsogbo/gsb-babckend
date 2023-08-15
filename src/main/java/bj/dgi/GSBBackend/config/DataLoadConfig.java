package bj.dgi.GSBBackend.config;


import bj.dgi.GSBBackend.entities.Role;
import bj.dgi.GSBBackend.entities.User;
import bj.dgi.GSBBackend.enums.RoleName;
import bj.dgi.GSBBackend.repositories.RoleRepository;
import bj.dgi.GSBBackend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataLoadConfig {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;

    public DataLoadConfig(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData() {
        loadRoles();
        createAdmin() ;

    }

    public void loadRoles() {
        for (RoleName roleName : RoleName.values()) {
            boolean exist=roleRepository.existsByName(roleName);
            if(!exist)
            {
                roleRepository.save(new Role(roleName));
            }
        }
    }

    public void createAdmin() {

            boolean exist=userRepository.existsByUsername("ADMIN");
            if(!exist)
            {
                User user =  new User() ;
                user.setHasDefaultPassword(false);
                user.setEmail("admin@admin.com");
                user.setUsername("ADMIN");
                user.setName("ADMIN");
                user.setPassword(passwordEncoder.encode("ADMIN"));

                userRepository.save(user) ;
            }

    }

}
