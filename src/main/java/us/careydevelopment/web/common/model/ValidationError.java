package us.careydevelopment.web.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ValidationError {

    private String code;
    private String field;
    private String message;
    
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
