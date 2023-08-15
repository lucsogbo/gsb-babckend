package bj.dgi.GSBBackend.payload;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
public class ApiResponse implements Serializable {

    private Boolean success;
    private String message;
    private Object object;
    private Integer statusCode;

    public ApiResponse(Boolean success, String message,Object object) {
        this.success = success;
        this.message = message;
        this.object= object ;
    }
    public ApiResponse(Boolean success, String message,Integer statusCode) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode ;
    }
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public ApiResponse(Boolean success, String message,Integer statusCode , Object object) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode ;
        this.object= object ;
    }


    public static ResponseEntity<?> Res200(Object objet){
        return new ResponseEntity<>(new ApiResponse(true,"",HttpStatus.OK.value(),objet),
                HttpStatus.OK) ;
    }

    public static ResponseEntity<?> Res200withMsg(String msg , Object objet){
        return new ResponseEntity<>( new ApiResponse(true,msg,HttpStatus.OK.value(),objet),
                HttpStatus.OK ) ;
    }

    public static ResponseEntity<?> Res500(String msg){
        return new ResponseEntity<>( new ApiResponse(false,msg,HttpStatus.INTERNAL_SERVER_ERROR.value(),null) ,
                HttpStatus.INTERNAL_SERVER_ERROR ) ;
    }

    public static ResponseEntity<?> Res400(String msg){
        return  new ResponseEntity<>(new ApiResponse(false,msg,HttpStatus.BAD_REQUEST.value(),null),
                HttpStatus.BAD_REQUEST ) ;
    }
    public static ResponseEntity<?> Res404(String msg){
        return  new ResponseEntity<>(new ApiResponse(false,msg,HttpStatus.NOT_FOUND.value(),null),
                HttpStatus.NOT_FOUND ) ;
    }





}