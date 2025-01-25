package com.sb.app.sb_crud.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.app.sb_crud.entities.User;
import com.sb.app.sb_crud.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService _userService;

    @GetMapping("/all")
    public ResponseEntity<?> getMethodName() {

        return ResponseEntity.ok().body(_userService.findall());

    }

    @PostMapping("/save")
    public ResponseEntity<?> getMethodName(@Valid @RequestBody User user, BindingResult result){

        if(result.hasFieldErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(_userService.save(user));
        
    }

     private ResponseEntity<Map<String, String>> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Field: " + err.getField()+" "+ err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);

    }
    


}
