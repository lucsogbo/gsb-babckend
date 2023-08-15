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
public class Recharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private Date date;
    private int montant;
    private double Kwh;
    @ManyToOne
    private C_electricite compteur;
}
