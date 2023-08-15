package bj.dgi.GSBBackend.payload.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType;

}
