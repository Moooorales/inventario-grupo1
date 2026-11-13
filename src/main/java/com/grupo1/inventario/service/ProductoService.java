package com.grupo1.inventario.service;

import com.grupo1.inventario.entity.Producto;
import com.grupo1.inventario.exception.ResourceNotFoundException;
import com.grupo1.inventario.repository.ProductoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Registrar un nuevo producto.
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar un producto existente.
    @Transactional
    public Producto actualizarProducto(Long id, Producto productoDetalles) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + id));

        producto.setNombre(productoDetalles.getNombre());
        producto.setDescripcion(productoDetalles.getDescripcion());
        producto.setPrecio(productoDetalles.getPrecio());
        producto.setStockActual(productoDetalles.getStockActual());
        producto.setStockMinimo(productoDetalles.getStockMinimo());
        producto.setCategoria(productoDetalles.getCategoria());
        producto.setProveedor(productoDetalles.getProveedor());

        return productoRepository.save(producto);
    }
}
