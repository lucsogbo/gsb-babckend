package bj.dgi.GSBBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private String nom;
    private String type_site;
    private String situation_geographique;
    private boolean levee_topographique;
    private double superficie;
    private boolean titre_foncier;
    private int numero_titre_foncier;
    private int numero_convention_vente;
    @ManyToOne
    private Fichier fichier;
    @ManyToOne
    @JsonIgnoreProperties(value = "ville", allowSetters = true)
    private Quartier quartier;

}
