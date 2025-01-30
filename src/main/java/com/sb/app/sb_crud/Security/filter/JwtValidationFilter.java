package com.sb.app.sb_crud.Security.filter;

import static com.sb.app.sb_crud.Security.TokenJwtConfig.HEADER_AUTH;
import static com.sb.app.sb_crud.Security.TokenJwtConfig.PREFIX_TOKEN;
import static com.sb.app.sb_crud.Security.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager) {

        super(authenticationManager);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
                String header = request.getHeader(HEADER_AUTH);

                if(header == null || !header.startsWith(PREFIX_TOKEN)){
                    return;
                }

                String token = header.replace(PREFIX_TOKEN, "");

                try{
                Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            }catch(JwtException e){
                
            }
        }

    

}
