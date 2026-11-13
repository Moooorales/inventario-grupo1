package com.grupo1.inventario.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    // Se ejecuta antes de procesar cada solicitud.
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        // Guardar el instante de inicio de la solicitud.
        long tiempoInicio = System.nanoTime();

        request.setAttribute("tiempoInicio", tiempoInicio);

        System.out.println("===== SOLICITUD RECIBIDA =====");
        System.out.println("Método HTTP: " + request.getMethod());
        System.out.println("Ruta: " + request.getRequestURI());

        return true;
    }

    // Se ejecuta después de completar la solicitud.
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        // Recuperar el instante de inicio.
        Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");

        if (tiempoInicio != null) {

            // Calcular el tiempo transcurrido en milisegundos.
            long duracion = (System.nanoTime() - tiempoInicio) / 1_000_000;

            System.out.println("Código HTTP: " + response.getStatus());
            System.out.println("Tiempo de ejecución: " + duracion + " ms");

            if (ex != null) {
                System.out.println("Error: " + ex.getMessage());
            }

            System.out.println("==============================");
        }
    }
}

