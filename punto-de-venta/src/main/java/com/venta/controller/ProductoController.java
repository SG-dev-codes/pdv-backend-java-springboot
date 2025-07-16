package com.venta.controller;

import com.venta.model.Producto;
import com.venta.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@Slf4j
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/obtenerProductos")
    public List<Producto> listar(){
        log.info("Ejecutando metrodo para la busqueda de productos");
        return productoService.obtenerproductos();
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
