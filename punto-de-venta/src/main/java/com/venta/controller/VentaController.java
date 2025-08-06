package com.venta.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venta.dto.VentaDTO;
import com.venta.model.Venta;
import com.venta.service.VentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
@Slf4j
public class VentaController {

    private final VentaService ventaService;

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarVenta(@RequestBody VentaDTO ventaDTO) {
        log.info("----Entranda a realizarVentas()----");
        try {
            ObjectMapper mapper = new ObjectMapper();
            String productosJson = mapper.writeValueAsString(ventaDTO.getProductos());

            Venta venta = new Venta();
            venta.setEmpleadoId(ventaDTO.getEmpleadoId());
            venta.setEmpleadoNombre(ventaDTO.getEmpleadoNombre());
            venta.setProductosJson(productosJson);
            venta.setFecha(java.time.LocalDateTime.now());
            venta.setVentaTotal(ventaDTO.getTotalVenta());

            Venta ventaGuardada = ventaService.guardarVenta(venta);
            return ResponseEntity.ok(ventaGuardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/detalle-ventas")
    public  ResponseEntity<List<Venta>> obtenerVentas() {
        log.info("----Entranda a obtenerVentas()----");
    return ResponseEntity.ok(ventaService.obtenerVentas());
    }

}
