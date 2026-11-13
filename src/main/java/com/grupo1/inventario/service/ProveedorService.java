package com.grupo1.inventario.service;

import com.grupo1.inventario.entity.Proveedor;
import com.grupo1.inventario.exception.ResourceNotFoundException;
import com.grupo1.inventario.repository.ProveedorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    // Registrar un nuevo proveedor.
    @Transactional
    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // Actualizar un proveedor existente.
    @Transactional
    public Proveedor actualizarProveedor(Long id, Proveedor proveedorDetalles) {

        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Proveedor no encontrado con ID: " + id));

        proveedor.setNombre(proveedorDetalles.getNombre());
        proveedor.setContacto(proveedorDetalles.getContacto());
        proveedor.setTelefono(proveedorDetalles.getTelefono());
        proveedor.setEmail(proveedorDetalles.getEmail());

        return proveedorRepository.save(proveedor);
    }
}
