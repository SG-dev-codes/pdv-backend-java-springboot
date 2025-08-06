package com.venta.service;

import com.venta.model.Producto;
import com.venta.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;


    public Page<Producto> obtenerProductosPaginado(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productoRepository.findAll(pageRequest);
    }


    public Producto crearProducto(Producto producto){
        producto.setActivo(true);
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto datos) {
        Producto existente = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setStock(datos.getStock());
        if (datos.getStock() == 0) {
            existente.setActivo(false);
        } else {
            existente.setActivo(true);
        }
        return productoRepository.save(existente);
    }

    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        productoRepository.save(producto);

    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(nombre);
        if (productos.isEmpty()) {
            throw new RuntimeException("No se encontraron productos con nombre: " + nombre);
        }
        return productos;
    }
}
