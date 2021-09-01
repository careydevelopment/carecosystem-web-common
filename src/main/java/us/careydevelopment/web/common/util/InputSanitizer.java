package us.careydevelopment.web.common.util;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputSanitizer {
    
    private static final Logger LOG = LoggerFactory.getLogger(InputSanitizer.class);

    
    public static void sanitizeBasic(Object obj) {
        Class<?> klazz = obj.getClass();
        Method[] methods = klazz.getDeclaredMethods();
        
        for (Method method : methods) {
            String name = method.getName();
            
            if (name != null && name.startsWith("get")) {
                Class<?> returnType = method.getReturnType();
                
                if (returnType.equals(String.class)) {
                    try {
                        String value = (String)method.invoke(obj);
                        if (value != null) {
                            value = value.replaceAll("[^A-Za-z0-9 '/&#,.-]","").trim();
                            Method setter = getEquivalentSetter(name, klazz);
                            setter.invoke(obj, value);    
                        }
                    } catch (Exception e) {
                        LOG.error("Problem retrieving value from method " + name, e);
                    }
                }
            }
        }
    }
    
    
    private static Method getEquivalentSetter(String getterName, Class<?> klazz) throws NoSuchMethodException {
        String methodName = "set" + getterName.substring(3);
        Method method = klazz.getMethod(methodName, String.class);
        return method;
    }
}
