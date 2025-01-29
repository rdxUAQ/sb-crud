package com.sb.app.sb_crud.Security.filter;



import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.app.sb_crud.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    

  

    

}
