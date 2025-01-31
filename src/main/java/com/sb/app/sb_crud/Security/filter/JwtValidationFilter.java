package com.sb.app.sb_crud.Security.filter;

import static com.sb.app.sb_crud.Security.TokenJwtConfig.CONTENT_TYPE;
import static com.sb.app.sb_crud.Security.TokenJwtConfig.HEADER_AUTH;
import static com.sb.app.sb_crud.Security.TokenJwtConfig.PREFIX_TOKEN;
import static com.sb.app.sb_crud.Security.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.security.Security;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.app.sb_crud.Security.SimpleGrantedAuthoritieJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
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
                String username = claims.getSubject();
                Object authClaims = claims.get("authorities");

                //roles
                Collection<? extends GrantedAuthority> authorities = Arrays.asList( 
                    new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritieJsonCreator.class)
                    .readValue(authClaims.toString().getBytes(),
                     SimpleGrantedAuthority[].class)
                    );

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                chain.doFilter(request, response);

            }catch(JwtException e){

                Map<String, String> body = new HashMap<>();
                body.put("error", e.getMessage());
                body.put("message", "Invalid JWT");

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(CONTENT_TYPE);

            }
        }

    

}
