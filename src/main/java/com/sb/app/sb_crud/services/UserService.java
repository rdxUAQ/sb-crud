package com.sb.app.sb_crud.services;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.app.sb_crud.entities.Role;
import com.sb.app.sb_crud.entities.User;
import com.sb.app.sb_crud.repositories.RoleRepository;
import com.sb.app.sb_crud.repositories.UserRepository;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository _UserRepository;

    @Autowired
    private RoleRepository _RoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findall() {

        return (List<User>)_UserRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {

        //validation
        if(_UserRepository.findByUserNameName(user.getUsername()) != null){

            return null;
        }

        Optional<Role> optionalRoleUser = _RoleRepository.findByName("ROLE_USER");

        Set<Role> roles = new HashSet<>();


        //optionalRoleUser.ifPresent(role -> roles.add(role));
        //same as 
        optionalRoleUser.ifPresent(roles::add);

        if(user.isAdmin()){

            Optional<Role> optionalRoleAdmin = _RoleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);

        }

        user.setRoles(roles);

        String passwordEncoded = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordEncoded);
        
        return _UserRepository.save(user);
    }

}
