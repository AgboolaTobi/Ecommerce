package com.task1.ecommerce.exceptions;

public class EmptyCartException extends Throwable {
    public EmptyCartException(String message) {
        super(message);
    }
}
