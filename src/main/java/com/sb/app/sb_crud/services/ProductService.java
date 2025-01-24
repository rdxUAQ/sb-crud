package com.sb.app.sb_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.app.sb_crud.entities.Product;
import com.sb.app.sb_crud.repositories.ProductRepository;
import com.sb.app.schemas.ProductSchema;



@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository _productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {

        return (List<Product>) _productRepository.findAll();

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return _productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return _productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Long id, ProductSchema productSchema) {

        Optional<Product> OptionalProduct = _productRepository.findById(id);

        if(!OptionalProduct.isPresent()){

            return null;

        }
        Product productUpdt = OptionalProduct.get();

        productUpdt.setPrice(productSchema.getPrice());
        productUpdt.setName(productSchema.getName());
        productUpdt.setInscription(productSchema.getInscription());

        return productUpdt;
    }

    @Transactional
    @Override
    public Optional<Product> deleteBy(Long id) {

        Optional<Product> productDb = _productRepository.findById(id);
        productDb.ifPresent(prod -> {

            _productRepository.delete(prod);

        });

        return productDb;


    }

    

}
