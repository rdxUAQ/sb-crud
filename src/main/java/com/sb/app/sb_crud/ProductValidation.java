package com.sb.app.sb_crud;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sb.app.sb_crud.entities.Product;

@Component
public class ProductValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product =(Product) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "PROD001","Name can not be null, emtpy or white space");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inscription", "PROD002","Inscription can not be null, emtpy or white space");
        
        if (product.getPrice() == null) {
            errors.rejectValue("price", "PROD003", "PRICE CAN NOT BE NULL");
        } else if (product.getPrice() < 0) {
            errors.rejectValue("price", "PROD004", "PRICE CAN NOT BE NEGATIVE VALUE");
        }
    }
}
