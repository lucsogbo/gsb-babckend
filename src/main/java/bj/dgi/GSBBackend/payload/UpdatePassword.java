package bj.dgi.GSBBackend.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePassword {

    @NotBlank
    String ancienPwd ;

    @NotBlank
    String nouveauPwd ;

    UpdatePassword( String ancienPwd , String nouveauPwd ){
        this.ancienPwd = ancienPwd ;
        this.nouveauPwd = nouveauPwd ;
    }

}
