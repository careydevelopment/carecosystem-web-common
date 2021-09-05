package us.careydevelopment.web.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.SignatureException;
import us.careydevelopment.util.webclient.ServiceException;

@ControllerAdvice
public class ExceptionController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(ServiceException.class)
    public String handleServiceException(ServiceException serviceException) {
        LOG.error("Caught ServiceException!", serviceException);
        
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
        LOG.error("Caught SignatureException!", ex);
        
        return "unauthorized";
    }
    
    
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex) {
        LOG.error("Caught unexpected error!", ex);
        
        return "unexpected-error";
    }
}
