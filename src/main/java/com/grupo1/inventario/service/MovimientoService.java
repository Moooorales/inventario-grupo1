package com.grupo1.inventario.service;

import com.grupo1.inventario.dto.MovimientoDTO;
import com.grupo1.inventario.entity.MovimientoInventario;
import com.grupo1.inventario.entity.Producto;
import com.grupo1.inventario.exception.InvalidMovementException;
import com.grupo1.inventario.exception.ResourceNotFoundException;
import com.grupo1.inventario.exception.StockInsuficienteException;
import com.grupo1.inventario.repository.MovimientoInventarioRepository;
import com.grupo1.inventario.repository.ProductoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MovimientoService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public MovimientoService(
            MovimientoInventarioRepository movimientoRepository,
            ProductoRepository productoRepository) {

        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoDTO dto) {

        // Validar que la cantidad sea positiva.
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new InvalidMovementException(
                    "La cantidad del movimiento debe ser mayor que cero.");
        }
        // Validar que el tipo de movimiento sea correcto.
        if (dto.getTipo() == null ||
                (!dto.getTipo().equalsIgnoreCase("ENTRADA")
                && !dto.getTipo().equalsIgnoreCase("SALIDA"))) {

            throw new InvalidMovementException(
                    "Tipo de movimiento no válido. Debe ser ENTRADA o SALIDA.");
        }

        // Buscar el producto por su ID.
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "El producto con ID " + dto.getProductoId() + " no existe."));

        // Procesar una salida de productos.
        if ("SALIDA".equalsIgnoreCase(dto.getTipo())) {

            // Comprobar que exista suficiente stock.
            if (producto.getStockActual() < dto.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente. Stock actual: "
                        + producto.getStockActual()
                        + ", cantidad solicitada: "
                        + dto.getCantidad());
            }

            producto.setStockActual(
                    producto.getStockActual() - dto.getCantidad());

        } else {

            // Procesar una entrada de productos.
            producto.setStockActual(
                    producto.getStockActual() + dto.getCantidad());
        }

        // Guardar el stock actualizado.
        productoRepository.save(producto);

        // Crear el registro del movimiento.
        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setProducto(producto);

        movimiento.setTipo(
                MovimientoInventario.TipoMovimiento.valueOf(
                        dto.getTipo().toUpperCase()));

        movimiento.setCantidad(dto.getCantidad());
        movimiento.setFechaMovimiento(LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }
}
