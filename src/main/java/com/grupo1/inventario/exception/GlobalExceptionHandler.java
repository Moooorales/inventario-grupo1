
package com.grupo1.inventario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Manejador centralizado de excepciones de toda la API REST.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja los errores cuando no se encuentra un recurso.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(
            ResourceNotFoundException ex) {

        return crearRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Maneja los errores cuando no existe suficiente stock.
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> manejarStockInsuficiente(
            StockInsuficienteException ex) {

        return crearRespuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    // Maneja los movimientos inválidos.
    @ExceptionHandler(InvalidMovementException.class)
    public ResponseEntity<Map<String, Object>> manejarMovimientoInvalido(
            InvalidMovementException ex) {

        return crearRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Construye una respuesta JSON uniforme para los errores.
    private ResponseEntity<Map<String, Object>> crearRespuesta(
            HttpStatus estado, String mensaje) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("estado", estado.value());
        respuesta.put("error", estado.getReasonPhrase());
        respuesta.put("mensaje", mensaje);

        return ResponseEntity.status(estado).body(respuesta);
    }
}

