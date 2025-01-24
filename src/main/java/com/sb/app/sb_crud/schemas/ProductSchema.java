package com.sb.app.sb_crud.schemas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductSchema {

    

    @NotBlank(message = "{NotEmpty.productschema.name}")
    @NotEmpty(message = "{NotEmpty.productschema.name}")
    @Size(min = 3, max = 50 )
    private String name;

    @Min(value = 0, message = "{NotNull.product.price}")
    @NotNull(message = "{NotNull.product.price}")
    private Long price;

    @NotBlank(message = "{NotEmpty.product.inscription}")
    @NotEmpty(message = "{NotEmpty.product.inscription}")
    private String inscription;

    // Constructor sin argumentos
    public ProductSchema() {
    }

    // Constructor con argumentos
    public ProductSchema(String name, Long price, String inscription) {
        this.name = name;
        this.price = price;
        this.inscription = inscription;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public String getInscription() {
        return inscription;
    }
    public void setInscription(String inscription) {
        this.inscription = inscription;
    } 

}
