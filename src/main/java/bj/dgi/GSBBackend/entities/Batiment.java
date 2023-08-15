package bj.dgi.GSBBackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Batiment extends Site implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type_batiment;
    private int loyer;
    private String situation_juridique;
    private String etat;
    private Date date_debut_traveau;
    private Date date_fin_traveau;
    private Date reception;
    private Date mise_en_service;
    private String observation;


}


//    public Batiment() {
//    }
//
//    public Batiment(String type_batiment, int loyer, String situation_juridique, String etat,
//                    Date date_debut_traveau, Date date_fin_traveau, Date reception, Date mise_en_service,
//                    String observation) {
//        this.type_batiment = type_batiment;
//        this.loyer = loyer;
//        this.situation_juridique = situation_juridique;
//        this.etat = etat;
//        this.date_debut_traveau = date_debut_traveau;
//        this.date_fin_traveau = date_fin_traveau;
//        this.reception = reception;
//        this.mise_en_service = mise_en_service;
//        this.observation = observation;
//    }
//
//    public String getType_batiment() {
//        return type_batiment;
//    }
//
//    public void setType_batiment(String type_batiment) {
//        this.type_batiment = type_batiment;
//    }
//
//    public int getLoyer() {
//        return loyer;
//    }
//
//    public void setLoyer(int loyer) {
//        this.loyer = loyer;
//    }
//
//    public String getSituation_juridique() {
//        return situation_juridique;
//    }
//
//    public void setSituation_juridique(String situation_juridique) {
//        this.situation_juridique = situation_juridique;
//    }
//
//    public String getEtat() {
//        return etat;
//    }
//
//    public void setEtat(String etat) {
//        this.etat = etat;
//    }
//
//    public Date getDate_debut_traveau() {
//        return date_debut_traveau;
//    }
//
//    public void setDate_debut_traveau(Date date_debut_traveau) {
//        this.date_debut_traveau = date_debut_traveau;
//    }
//
//    public Date getDate_fin_traveau() {
//        return date_fin_traveau;
//    }
//
//    public void setDate_fin_traveau(Date date_fin_traveau) {
//        this.date_fin_traveau = date_fin_traveau;
//    }
//
//    public Date getReception() {
//        return reception;
//    }
//
//    public void setReception(Date reception) {
//        this.reception = reception;
//    }
//
//    public Date getMise_en_service() {
//        return mise_en_service;
//    }
//
//    public void setMise_en_service(Date mise_en_service) {
//        this.mise_en_service = mise_en_service;
//    }
//
//    public String getObservation() {
//        return observation;
//    }
//
//    public void setObservation(String observation) {
//        this.observation = observation;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Batiment batiment = (Batiment) o;
//        return loyer == batiment.loyer && Objects.equals(type_batiment, batiment.type_batiment) && Objects.equals(situation_juridique, batiment.situation_juridique) && Objects.equals(etat, batiment.etat) && Objects.equals(date_debut_traveau, batiment.date_debut_traveau) && Objects.equals(date_fin_traveau, batiment.date_fin_traveau) && Objects.equals(reception, batiment.reception) && Objects.equals(mise_en_service, batiment.mise_en_service) && Objects.equals(observation, batiment.observation);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(type_batiment, loyer, situation_juridique, etat, date_debut_traveau, date_fin_traveau, reception, mise_en_service, observation);
//    }
//
//    @Override
//    public String toString() {
//        return "Batiment{" +
//                "type_batiment='" + type_batiment + '\'' +
//                ", loyer=" + loyer +
//                ", situation_juridique='" + situation_juridique + '\'' +
//                ", etat='" + etat + '\'' +
//                ", date_debut_traveau=" + date_debut_traveau +
//                ", date_fin_traveau=" + date_fin_traveau +
//                ", reception=" + reception +
//                ", mise_en_service=" + mise_en_service +
//                ", observation='" + observation + '\'' +
//                '}';
//    }
//}
