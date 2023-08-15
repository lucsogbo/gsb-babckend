package bj.dgi.GSBBackend.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class Fusion {
    private List<?> reussi;
    private List<?> echoue;
    public Fusion(List<?> reussi,List<?> echoue){
        this.reussi = reussi;
        this.echoue = echoue;
    }
}
