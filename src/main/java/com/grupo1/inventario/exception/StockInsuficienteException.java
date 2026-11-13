package com.grupo1.inventario.exception;

// Excepción para controlar cuando no existe suficiente stock.
public class StockInsuficienteException extends RuntimeException {

    // Constructor que recibe el mensaje del error.
    public StockInsuficienteException(String message) {
        super(message);
    }
}
