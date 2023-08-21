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
public class Fichier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String ref_piece;
    private Date date_save = new Date();
    private String urlDownload;
    @ManyToOne
    private TypeFichier typeFichier;
    @ManyToOne
    private Batiment batiment;
    @ManyToOne
    private Parcelle parcelle;
    @ManyToOne
    private Facture facture;





}
