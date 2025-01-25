package com.sb.app.sb_crud.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sb.app.sb_crud.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

    Optional<Role> findByName(String name);

}
