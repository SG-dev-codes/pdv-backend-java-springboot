package com.venta.controller;
import com.venta.model.Producto;
import com.venta.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@Slf4j
public class ProductoController {
    private final ProductoService productoService;
    @GetMapping("/obtenerProductos")
    public ResponseEntity<Map<String, Object>> obtenerProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Producto> pageProductos = productoService.obtenerProductosPaginado(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("productos", pageProductos.getContent());
        response.put("totalPaginas", pageProductos.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarProducto(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre) {
        log.info("-----Ejecutando metodo para buscar productos paginados-----");
        try {
            if (id != null) {
                Producto producto = productoService.buscarPorId(id);
                return ResponseEntity.ok(producto);
            } else if (nombre != null && !nombre.isBlank()) {
                List<Producto> productos = productoService.buscarPorNombre(nombre);
                return ResponseEntity.ok(productos);
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Debes proporcionar un id o nombre para buscar"));
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }





    @PostMapping("/crear")
    public Producto crear(@RequestBody Producto producto){
        log.info("Ejecutando metodo para crear productos");
        return productoService.crearProducto(producto);

    }

    @PutMapping("/actualizar/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto){
        log.info("Ejecutando metodo para actualizar productos");
        return productoService.actualizarProducto(id,producto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        log.info("Ejecutando para eliminar productos");
        productoService.eliminarProducto(id);
    }

}
