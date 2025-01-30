package com.sb.app.sb_crud.Security.filter;



import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.app.sb_crud.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.sb.app.sb_crud.Security.TokenJwtConfig.*;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager _authenticationManager;

    

    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        _authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

                User user = null;
                String userName = null;
                String password = null;

                try {
                    user = new ObjectMapper().readValue(request.getInputStream(), User.class);
                    userName = user.getUsername();
                    password = user.getPassword();

                } catch (StreamReadException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (DatabindException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password);

                return _authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
                String userName = user.getUsername();
                Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

                Claims claims = Jwts.claims().add("authorities", roles).build();
                

                String token = Jwts.builder()
                .subject(userName)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

                response.addHeader(HEADER_AUTH, PREFIX_TOKEN + token);

                Map<String, String> body = new HashMap<>();
                body.put("token", token);
                body.put("username", userName);

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setContentType(CONTENT_TYPE);
                response.setStatus(200);


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        
                Map<String, String> body = new HashMap<>();
                body.put("message", "Error in auth check and retry with correct credentials");
                body.put("error", failed.getMessage());

                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(401);
                response.setContentType(CONTENT_TYPE);
    }

    

    

    

  

    

}
