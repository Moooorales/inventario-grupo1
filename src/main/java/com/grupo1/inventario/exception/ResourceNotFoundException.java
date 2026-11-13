package com.grupo1.inventario.exception;

// Excepción para indicar que un recurso solicitado no existe.
public class ResourceNotFoundException extends RuntimeException {

    // Constructor que recibe el mensaje del error.
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

