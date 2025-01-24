package com.sb.app.sb_crud.services;

import java.util.List;
import java.util.Optional;

import com.sb.app.sb_crud.entities.Product;
import com.sb.app.sb_crud.schemas.ProductSchema;

public interface IProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Long id, ProductSchema productSchema);

    Optional<Product> deleteBy(Long id);

}
