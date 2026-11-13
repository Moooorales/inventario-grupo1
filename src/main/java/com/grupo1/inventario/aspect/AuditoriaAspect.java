package com.grupo1.inventario.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditoriaAspect {

    // Registrar automáticamente la ejecución de los métodos
    // que pertenecen a los servicios del sistema.
    @Around("execution(* com.grupo1.inventario.service..*(..))")
    public Object registrarOperacion(ProceedingJoinPoint joinPoint)
            throws Throwable {

        // Obtener el nombre del método ejecutado.
        String metodo = joinPoint.getSignature().toShortString();

        // Registrar el instante de inicio.
        long inicio = System.nanoTime();

        System.out.println("===== AUDITORÍA AOP =====");
        System.out.println("Iniciando operación: " + metodo);

        try {

            // Ejecutar el método original del servicio.
            Object resultado = joinPoint.proceed();

            // Calcular el tiempo de ejecución.
            long duracion = (System.nanoTime() - inicio) / 1_000_000;

            System.out.println("Resultado: Operación exitosa");
            System.out.println("Tiempo de ejecución: " + duracion + " ms");

            return resultado;

        } catch (Throwable ex) {

            // Registrar si la operación produjo un error.
            long duracion = (System.nanoTime() - inicio) / 1_000_000;

            System.out.println("Resultado: Operación fallida");
            System.out.println("Error: " + ex.getMessage());
            System.out.println("Tiempo hasta el error: " + duracion + " ms");

            // Mantener el comportamiento original de la excepción.
            throw ex;

        } finally {
            System.out.println("=========================");
        }
    }
}
