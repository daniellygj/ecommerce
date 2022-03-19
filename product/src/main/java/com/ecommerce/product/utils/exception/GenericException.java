package com.ecommerce.product.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GenericException extends Throwable {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String object, Long id) {
            super(object + " with id " + id + " does not exists.");
        }
    }
}
