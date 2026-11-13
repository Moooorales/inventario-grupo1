package com.grupo1.inventario.exception;

// Excepción para controlar movimientos de inventario inválidos.
public class InvalidMovementException extends RuntimeException {

    // Constructor que recibe el mensaje del error.
    public InvalidMovementException(String message) {
        super(message);
    }
}

