package com.sb.app.sb_crud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sb.app.sb_crud.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
