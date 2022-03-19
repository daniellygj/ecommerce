package com.ecommerce.product.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InventoryException extends Throwable {

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    public static class NotEnoughtStockException extends RuntimeException {
        public NotEnoughtStockException(Long id, int qty) {
            super("The Product with id " +  id + " does not have " + qty + " units in stock.");
        }
    }
}
