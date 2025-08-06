package com.venta.model;

import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Data
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empleado_id", nullable = false)
    private Long empleadoId;

    @Column(name = "empleado_nombre", nullable = false)
    private String empleadoNombre;

    @Column(name = "productos_json", columnDefinition = "TEXT", nullable = false)
    private String productosJson;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "venta_total", nullable = false)
    private Double ventaTotal;
}
