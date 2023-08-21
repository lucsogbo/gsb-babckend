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
public class Parcelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private boolean bati;
    private boolean louer;
    private boolean don;
    private String situation_geographique;
    private String situation_juridique;
    private boolean levee_topographique;
    private double superficie;
    private boolean titre_foncier;
    private String reference_titre_foncier;
    private String reference_convention_vente;
    private String reference_acte_donation;
    @ManyToOne
    @JsonIgnoreProperties(value = "commune", allowSetters = true)
    private Quartier quartier;
    @ManyToOne
    private Structure structure;


}
