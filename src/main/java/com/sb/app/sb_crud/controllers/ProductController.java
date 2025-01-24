package com.sb.app.sb_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.sb.app.sb_crud.entities.Product;
import com.sb.app.sb_crud.services.ProductService;
import com.sb.app.schemas.ProductSchema;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RequestMapping("api/v1/product")
@RestController
public class ProductController {
    
    @Autowired
    private ProductService _productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){

        List<Product> list = _productService.findAll();

        if(list.size() > 0){
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){

        Optional<Product> productO = _productService.findById(id);

        if(productO.isPresent()){

            return ResponseEntity.ok(productO.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct( @Valid @RequestBody Product product, BindingResult result) {

        if(result.hasFieldErrors()){
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(_productService.save(product));
        

    }
    

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct( @Valid @RequestBody ProductSchema productSchema, BindingResult resultB, @PathVariable Long id) {
        
        if(resultB.hasFieldErrors()){
            return validation(resultB);
        }
        var result = _productService.update(id, productSchema);

        if(result == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
       
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<Product> removeById(@PathVariable Long id) {
        

        Optional<Product> OptionalProduct = _productService.findById(id);

        if(!OptionalProduct.isPresent()){

            return ResponseEntity.notFound().build();

        }

        var result = _productService.deleteBy(id);

        if(result == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
       
        return ResponseEntity.ok().build();

    }

    /////////////////////////
    /// 
    private ResponseEntity<Map<String, String>> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Field: " + err.getField()+" "+ err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);

    }
    
    
    
    


}
