package us.careydevelopment.web.common.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import us.careydevelopment.web.common.constants.Authority;

@Component
public class SecurityUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityUtil.class);

    public static final String ANONYMOUS_USER_NAME = "anonymousUser";
    
    private static final List<String> VALID_ADMIN_AUTHORITIES = List.of(Authority.ADMIN_ECOSYSTEM_USER);
    
    
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
    
    
    public boolean isAdmin(String jwtToken) {
        boolean isAdmin = false;
        
        try {
            List<String> authorities = jwtUtil.getAuthoritiesFromToken(jwtToken);
            LOG.debug("User authorities is " + authorities);
            
            if (authorities != null) {
                for (String auth : VALID_ADMIN_AUTHORITIES) {
                    if (authorities.contains(auth)) {
                        LOG.debug("User is admin");
                        
                        isAdmin = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Problem trying to retrieve authorities for user!", e);
        }
        
        return isAdmin;
    }
}
