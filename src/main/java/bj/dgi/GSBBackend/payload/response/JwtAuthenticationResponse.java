package bj.dgi.GSBBackend.payload.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private boolean hasDefaultPassword=false;
    private String changePasswordToken="";

    public JwtAuthenticationResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public String toString() {
        return "JwtAuthenticationResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
