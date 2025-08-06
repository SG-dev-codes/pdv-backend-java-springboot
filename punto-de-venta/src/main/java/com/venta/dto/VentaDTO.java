package com.venta.dto;

import lombok.Data;

import java.util.List;

@Data
public class VentaDTO{
    private Long empleadoId;
    private String empleadoNombre;
    private List<ProductoVendidoDTO> productos;
    private Double totalVenta;
}
