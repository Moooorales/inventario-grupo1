package com.grupo1.inventario.repository;

import com.grupo1.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Consulta personalizada 1: Productos con stock por debajo del mínimo
    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo")
    List<Producto> obtenerProductosBajoStock();
}