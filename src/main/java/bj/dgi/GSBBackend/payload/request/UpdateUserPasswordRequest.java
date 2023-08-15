package bj.dgi.GSBBackend.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateUserPasswordRequest {
    @NotNull
    private String token;
    @NotNull


    @NotNull
    @Size(min = 8)
    private String newPassword;
    @NotNull
    @Size(min = 8)
    private String confirmPassword;


}
