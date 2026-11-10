package com.grupo1.inventario.repository;

import com.grupo1.inventario.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {

    // Consulta personalizada 2: Movimientos por producto ordenados por fecha desc
    @Query("SELECT m FROM MovimientoInventario m WHERE m.producto.id = :productoId ORDER BY m.fecha DESC")
    List<MovimientoInventario> obtenerMovimientosPorProducto(@Param("productoId") Long productoId);
}
