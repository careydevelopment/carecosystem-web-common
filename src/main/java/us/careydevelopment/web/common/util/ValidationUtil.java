package us.careydevelopment.web.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import us.careydevelopment.web.common.model.ValidationError;

public class ValidationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtil.class);
    
    public static Map<String, ValidationError> translateObjectErrorsToValidationErrors(String errorJson) throws JsonMappingException, JsonProcessingException {
        Map<String, ValidationError> errors = new HashMap<>();
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(errorJson);

        if (jsonNode.getNodeType().equals(JsonNodeType.ARRAY)) {
            jsonNode.elements().forEachRemaining(node -> {
                String code = getCode(node);
                String field = getField(node);
                String message = getMessage(node);
                
                ValidationError error = new ValidationError();
                error.setCode(code);
                error.setField(field);
                error.setMessage(message);
                
                errors.put(field, error);
            });
        } else {
            LOG.warn("ValidationUtil expected JSON array: " + errorJson);
        }
        
        return errors;
    }

    
    private static String getMessage(JsonNode node) {
        String message = "";
        
        JsonNode messageNode = node.get("defaultMessage");
        if (messageNode != null) {
            message = messageNode.asText();
        }
        
        return message;
    }
    
    
    private static String getField(JsonNode node) {
        String field = "";
        
        JsonNode fieldNode = node.get("field");
        if (fieldNode != null) {
            field = fieldNode.asText();
        }
        
        return field;
    }
    
    
    private static String getCode(JsonNode node) {
        String code = "";
        
        JsonNode argNode = node.get("arguments");
        if (argNode != null) {
            JsonNode arrayNode = argNode.get(0);
            
            if (arrayNode != null) {
                JsonNode codesNode = arrayNode.get("codes");
                
                if (codesNode != null) {
                    JsonNode codeNode = codesNode.get(0);
                    
                    if (codeNode != null) {
                        code = codeNode.asText();
                    }
                }
            }
        }
        
        return code;
    }
}
