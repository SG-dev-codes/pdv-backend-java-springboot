package com.venta.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venta.dto.ProductoVendidoDTO;
import com.venta.model.Producto;
import com.venta.model.Venta;
import com.venta.repository.ProductoRepository;
import com.venta.repository.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final ObjectMapper objectMapper;
    @Transactional
    public Venta guardarVenta(Venta venta) throws JsonProcessingException {
        log.info("----Entranda a servicio guardarVenta()----");
        // Convertir JSON string a lista
        List<ProductoVendidoDTO> productos = objectMapper.readValue(
                venta.getProductosJson(),
                new TypeReference<List<ProductoVendidoDTO>>() {}
        );

        // Validar y restar stock
        for (ProductoVendidoDTO pv : productos) {
            Producto producto = productoRepository.findById(pv.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + pv.getProductoId()));

            if (producto.getStock() < pv.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - pv.getCantidad());
            productoRepository.save(producto);
        }

        // Convertir la lista nuevamente a JSON para guardar
        String jsonProductos = objectMapper.writeValueAsString(productos);
        venta.setProductosJson(jsonProductos);

        // Guardar venta
        return ventaRepository.save(venta);
    }

    public List<Venta> obtenerVentas() {
        log.info("----Entranda a servicio obtenerVentas()----");
        return ventaRepository.findAll();
    }
}
