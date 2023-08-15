package bj.dgi.GSBBackend.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ApiResponseBis {
    private Boolean success;
    private String message;
    private Object object;
    private HttpStatus statusCode;
    private int statusCode1;
    private Resource fileStream;


    /**
     *
     * @param success
     * @param message
     * @param statusCode
     * @param object
     */
    public ApiResponseBis(Boolean success, String message, HttpStatus statusCode, Object object) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
    }

    public ApiResponseBis(Boolean success, String message, HttpStatus statusCode, Resource fileStream) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.fileStream = fileStream;
    }

    /**
     *
     * @param success
     * @param message
     * @param statusCode
     */
    public ApiResponseBis(Boolean success, String message, HttpStatus statusCode) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
    }



    /**
     *
     * @param success
     * @param message
     * @param statusCode
     * @param object
     */
    public ApiResponseBis(Boolean success, String message, int statusCode, Object object) {
        this.success = success;
        this.message = message;
        this.statusCode1 = statusCode;
        this.object = object;
    }

    /**
     *
     * @param success
     * @param message
     */
    public ApiResponseBis(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
