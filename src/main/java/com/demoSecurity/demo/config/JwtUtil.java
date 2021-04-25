package com.demoSecurity.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class JwtUtil {

    static void addAuthentication(HttpServletResponse res,String username){
        String token = Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512,"secret")
                .compact();
        res.addHeader("Authorization","Bearer "+token);
    }

    static Authentication getAuthentication(HttpServletRequest req){
        String token = req.getHeader("Authorization");

        if(token !=null){
            String user = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token.replace("Bearer",""))
                    .getBody()
                    .getSubject();

            return user != null ? new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList()):null;
        }
        return null;
    }
}
