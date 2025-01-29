package com.sb.app.sb_crud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.app.sb_crud.entities.User;
import com.sb.app.sb_crud.repositories.UserRepository;



@Service
public class JpaUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository _UserRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> userOpt =_UserRepository.findByUsername(username);

        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username: %s can not be located in the DB",username));
        }

        User user = userOpt.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

        

        return new org.springframework.security.core.userdetails
        .User(
            user.getUsername(), 
            user.getPassword(), 
            user.getEnabled(), 
            true,
            true,
            true, 
            authorities);
    }


}
