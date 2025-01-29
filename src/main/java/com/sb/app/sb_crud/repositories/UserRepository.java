package com.sb.app.sb_crud.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sb.app.sb_crud.entities.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long>{

    @Query("SELECT u.username from User u where u.username = ?1")
     String findByUserNameName(String name);

     boolean existsByUsername(String username);

     Optional<User> findByUsername(String username);


}
