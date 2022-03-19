package com.ecommerce.product.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProductException extends Throwable {

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public static class ProductDeleted extends RuntimeException {
        public ProductDeleted(Long id) {
            super("The Product with id " +  id + " was already deleted.");
        }
    }

}
