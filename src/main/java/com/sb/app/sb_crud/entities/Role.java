package com.sb.app.sb_crud.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Role() {
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    } 

    public String getName() {
        return name;
    }

    

    
}
