package com.grupo1.inventario.controller;

import com.grupo1.inventario.dto.MovimientoDTO;
import com.grupo1.inventario.entity.MovimientoInventario;
import com.grupo1.inventario.entity.Producto;
import com.grupo1.inventario.entity.Proveedor;
import com.grupo1.inventario.service.MovimientoService;
import com.grupo1.inventario.service.ProductoService;
import com.grupo1.inventario.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InventarioController {

    private final ProductoService productoService;
    private final ProveedorService proveedorService;
    private final MovimientoService movimientoService;

    public InventarioController(ProductoService productoService, ProveedorService proveedorService, MovimientoService movimientoService) {
        this.productoService = productoService;
        this.proveedorService = proveedorService;
        this.movimientoService = movimientoService;
    }

    // 1. POST /api/productos
    @PostMapping("/productos")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardarProducto(producto));
    }

    // 2. PUT /api/productos/{id}
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
    }

    // 3. POST /api/proveedores
    @PostMapping("/proveedores")
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorService.guardarProveedor(proveedor));
    }

    // 4. PUT /api/proveedores/{id}
    @PutMapping("/proveedores/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorService.actualizarProveedor(id, proveedor));
    }

    // 5. POST /api/movimientos (Con validación de stock negativo)
    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoInventario> registrarMovimiento(@RequestBody MovimientoDTO dto) {
        return ResponseEntity.ok(movimientoService.registrarMovimiento(dto));
    }
}