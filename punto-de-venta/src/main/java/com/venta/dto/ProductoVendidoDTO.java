package com.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoVendidoDTO {
    private Long productoId;
    private int cantidad;
    private String nombre;
    private int precioUnitario;
    private int total;

}
