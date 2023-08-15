package bj.dgi.GSBBackend.controller;

import bj.dgi.GSBBackend.entities.User;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.repositories.UserRepository;
import bj.dgi.GSBBackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

    @Autowired
    UserRepository userRepository ;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(new ApiResponse(false, "Bad request errors.", HttpStatus.BAD_REQUEST.value()
                , errors),HttpStatus.BAD_REQUEST);

    }

    User getUserConnected(){
//        if(SecurityContextHolder.getContext().getAuthentication()==null){
//
//        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Long id = ((UserPrincipal) authentication.getPrincipal()).getId();
            return  this.userRepository.findById(id).get();

        }
        return null ;
    }


}
