package com.informatorio.ecommerce.controller;

import com.informatorio.ecommerce.domain.Producto;
import com.informatorio.ecommerce.domain.Usuario;
import com.informatorio.ecommerce.repository.ProductoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;


@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    @PostMapping
    public ResponseEntity<?> createProducto(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(productoRepository.save(producto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void borrarProducto(@PathVariable("id") Long id) {
        productoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Producto modificarProducto(@PathVariable("id") Long id,
                                      @Valid @RequestBody Producto producto) {
        Producto productoModificado = productoRepository.getById(id);
        productoModificado.setNombre(producto.getNombre());
        productoModificado.setDescripcion(producto.getDescripcion());
        productoModificado.setPrecioUnitario(producto.getPrecioUnitario());
        productoModificado.setContenido(producto.getContenido());
        productoModificado.setPublicado(producto.getPublicado());
        return productoRepository.save(productoModificado);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosProductos(
            @RequestParam(name = "publicado", required = false) Boolean publicado,
            @RequestParam(name = "nombre", required = false) String nombre) {
        if(publicado != null) {
            return new ResponseEntity<>(productoRepository.findByPublicado(publicado), HttpStatus.OK);
        } else if (Objects.nonNull (nombre)) {
            return new ResponseEntity<>(productoRepository.findByNombreContaining(nombre), HttpStatus.OK);
        }
        return new ResponseEntity<>(productoRepository.findAll(), HttpStatus.OK);
    }
}
