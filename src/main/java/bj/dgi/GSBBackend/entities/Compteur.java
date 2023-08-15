package bj.dgi.GSBBackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero_police;
    private Date date_attribution;
    private boolean impaye;
    private int numero_compteur;
    private String observation;
    @ManyToOne
    private Abonne abonne;
    @ManyToOne
    private Batiment batiment;
}
