package com.grupo1.inventario.dto;

public class MovimientoDTO {
    private Long productoId;
    private String tipo; // "ENTRADA" o "SALIDA"
    private Integer cantidad;
    private String motivo;

    public MovimientoDTO() {}

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

}
