package us.careydevelopment.web.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import us.careydevelopment.web.common.model.ValidationError;

public class InputSanitizerTest {

    @Test
    public void inputSanitizerHappyPathTest() {
        ValidationError error = new ValidationError();
        error.setCode("code");
        error.setField("field*^%");
        error.setMessage("message<>");
        
        InputSanitizer.sanitizeBasic(error);
        
        Assertions.assertEquals("message", error.getMessage());
        Assertions.assertEquals("field", error.getField());
        Assertions.assertEquals("code", error.getCode());
    }
}
