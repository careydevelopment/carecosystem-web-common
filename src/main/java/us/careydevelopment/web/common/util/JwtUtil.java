package us.careydevelopment.web.common.util;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    
    private String jwtSecret;

    
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.jwtSecret = secret;
    }
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(Claims::getSubject, token);
    }

        
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(Claims::getExpiration, token);
    }


    public <T> T getClaimFromToken(Function<Claims, T> claimsResolver, String token) {
        final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
    }
        
        
    public String getClaimFromTokenByName(String name, String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return (String)claims.get(name);
    }
        
        
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

        
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }           

    
    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }
        
    
    //no return necessary as this will throw an exception if there's a problem
    public void validateTokenWithSignature(String token) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }
    
    
    public List<String> getAuthorities(Claims claims) {
        List<String> authorityNames = (List<String>) claims.get("authorities");
        return authorityNames;
    }
}
