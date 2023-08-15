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
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private Date debut_periode;
    private Date fin_priode;
    private double montant;
    private boolean paye;
    private Date echeance_paiement;
    @ManyToOne
    private Compteur compteur;

}
