package bj.dgi.GSBBackend.entities;


import bj.dgi.GSBBackend.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @NotBlank
    private String username;

    @NaturalId
    @NotBlank
    @Email
    private String email;


    @Size(max = 100)
    @JsonIgnore
    private String password;

    @Column(name = "has_default_password",columnDefinition = "boolean default false")
    private boolean hasDefaultPassword;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus= UserStatus.ENABLED ;

    Boolean actif=Boolean.TRUE  ;

    public boolean isActif(){
        if(this.actif==null){
            return true ;
        }
        return actif ;
    }

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User(){
        super();
    }


}
