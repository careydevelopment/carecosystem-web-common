package us.careydevelopment.web.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityUtil.class);

    public static final String ANONYMOUS_USER_NAME = "anonymousUser";
    
    @Autowired
    private JwtUtil jwtUtil;
        
    
    /**
     * Here, jwtToken was retrieved via @Cookie
     */
    public String getCurrentUser(String jwtToken) {
        String user = ANONYMOUS_USER_NAME;
        
        if (!StringUtils.isEmpty(jwtToken)) {
            //now check the cookie
            try {
                Boolean valid = jwtUtil.validateToken(jwtToken);
                
                if (valid) {
                    user = jwtUtil.getUsernameFromToken(jwtToken);
                }
            } catch (Exception e) {
                LOG.debug("JWT passed in via cookie not valid", e);
            }
        }

        return user;
    }
}
