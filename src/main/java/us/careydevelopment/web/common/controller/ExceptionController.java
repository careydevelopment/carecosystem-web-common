package us.careydevelopment.web.common.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.SignatureException;
import us.careydevelopment.util.webclient.ServiceException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(ServiceException serviceException) {
        String view = "unexpected-error";
        
        switch (serviceException.getStatusCode()) {
            case 401:
                view ="unauthorized";
                break;
        }
        
        
        return view;
    }
    
    
    @ExceptionHandler(SignatureException.class)
    public String handleSignatureException(SignatureException ex) {
        return "unauthorized";
    }
    
    
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex) {
        return "unexpected-error";
    }
}
