package bj.dgi.GSBBackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Terrain_nu extends Site{

    private Long id;
    private int numero_acte_donation;

}
