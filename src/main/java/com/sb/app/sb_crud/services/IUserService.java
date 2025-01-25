package com.sb.app.sb_crud.services;

import java.util.List;

import com.sb.app.sb_crud.entities.User;

public interface IUserService {

    List<User> findall();

    User save(User user);

}
