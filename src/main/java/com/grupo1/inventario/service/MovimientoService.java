package com.grupo1.inventario.service;

import com.grupo1.inventario.dto.MovimientoDTO;
import com.grupo1.inventario.entity.MovimientoInventario;
import com.grupo1.inventario.entity.Producto;
import com.grupo1.inventario.repository.MovimientoInventarioRepository;
import com.grupo1.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MovimientoService {
    private final MovimientoInventarioRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public MovimientoService(MovimientoInventarioRepository movimientoRepository, ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;

}
    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("El producto con ID " + dto.getProductoId() + " no existe."));

        // REGLA DE NEGOCIO: Impedir movimientos que produzcan stock negativo
        if ("SALIDA".equalsIgnoreCase(dto.getTipo())) {
            if (producto.getStockActual() < dto.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente. Stock actual: " 
                        + producto.getStockActual() + ", cantidad solicitada: " + dto.getCantidad());
            }
            producto.setStockActual(producto.getStockActual() - dto.getCantidad());
        } else if ("ENTRADA".equalsIgnoreCase(dto.getTipo())) {
            producto.setStockActual(producto.getStockActual() + dto.getCantidad());
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido. Debe ser ENTRADA o SALIDA.");
        }

        productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setTipo(MovimientoInventario.TipoMovimiento.valueOf(dto.getTipo().toUpperCase()));
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setFechaMovimiento(LocalDateTime.now());

        return movimientoRepository.save(movimiento);

    }
}