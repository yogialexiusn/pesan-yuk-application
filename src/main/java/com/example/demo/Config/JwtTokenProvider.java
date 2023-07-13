package com.example.demo.Config;

import com.example.demo.Services.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("JwtTokenProvider")
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String JWTSecret;

    @Value("${app.jwtExpire}")
    private int JWTExpiration;

    // KETIKA LOGIN, METHOD GENERATE TOKEN INI AKAN MELAUKAN SIGNATURE MENGGUNAKAN ALGOERITM JS512
    // DENGAN KEY JWT SECRET

    public String generateToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        Date now = new Date();
         // WAKTU EXPIREDNYA
        Date expiryDate = new Date(now.getTime() + JWTExpiration);

        return Jwts.builder()
                .setSubject(Long.toString(userPrinciple.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWTSecret)
                .compact();
    }

    // UNTUK MENGAMBIL DECODE JWT YANG DIAMBIL DARI CLIENT., LANGSUNG MENDAPATKAN ISINYA
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWTSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // VALIDASI TOKEN TERSEBUT
    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(JWTSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex){
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex){
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex){
            logger.error("JWT claims string is empty");
        }
        return false;
    }
}
