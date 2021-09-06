package com.informatorio.ecommerce.controller;

import com.informatorio.ecommerce.domain.Carrito;
import com.informatorio.ecommerce.domain.LineaDeCarrito;
import com.informatorio.ecommerce.domain.Producto;
import com.informatorio.ecommerce.domain.Usuario;
import com.informatorio.ecommerce.dto.OperacionCarrito;
import com.informatorio.ecommerce.repository.CarritoRepository;
import com.informatorio.ecommerce.repository.ProductoRepository;
import com.informatorio.ecommerce.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class CarritoController {

    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    public CarritoController(UsuarioRepository usuarioRepository,
                             CarritoRepository carritoRepository,
                             ProductoRepository productoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    //CREAR CARRITO
    @PostMapping("/usuario/{id}/carrito")
    public ResponseEntity<?> crearCarrito(@PathVariable("id") Long id,
                                           @Valid @RequestBody Carrito carrito) {

        Usuario usuario = usuarioRepository.getById(id);
        carrito.setUsuario(usuario);
        //List<Carrito> carritos_usuario = usuario.getCarritos();
        //carritos_usuario.add(carrito);

        return new ResponseEntity<>(carritoRepository.save(carrito), HttpStatus.CREATED);
    }

    //BORRAR CARRITO
    @DeleteMapping("/usuario/{id}/carrito/{idCarrito}")
    public void borrarCarrito(@PathVariable("id") Long id,
                              @PathVariable("idCarrito") Long idCarrito) {

        Usuario usuario = usuarioRepository.getById(id);
        Carrito carrito = carritoRepository.getById(idCarrito);
        carritoRepository.deleteById(idCarrito);
    }


    //AGREGAR PRODUCTOS AL CARRITO
    @PutMapping("/usuario/{id}/carrito/{idCarrito}")
    public ResponseEntity<?> agregarProducto(@PathVariable("id") Long id,
                                             @PathVariable("idCarrito") Long idCarrito,
                                             @Valid @RequestBody OperacionCarrito operacionCarrito) {
        Usuario usuario = usuarioRepository.getById(id);
        Carrito carrito = carritoRepository.getById(idCarrito);
        Producto producto = productoRepository.getById(operacionCarrito.getProductoId());
        LineaDeCarrito lineaDeCarrito = new LineaDeCarrito();
        lineaDeCarrito.setCarrito(carrito);
        lineaDeCarrito.setProducto(producto);
        lineaDeCarrito.setUsuario(usuario);
        lineaDeCarrito.setCantidad(operacionCarrito.getCantidad());
        lineaDeCarrito.setPrecioUnitario(producto.getPrecioUnitario());
        //lineaDeCarrito.setSubTotal(lineaDeCarrito.getSubTotal());
        carrito.agregarLineDeCarrito(lineaDeCarrito);
        return new ResponseEntity<>(carritoRepository.save(carrito), HttpStatus.OK);
    }


    /*
    @GetMapping("/carrito")
    public ResponseEntity<?> obtenerTodosLosCarritos() {
        return new ResponseEntity<>(carritoRepository.findAll(), HttpStatus.OK);
    }
    */

    //OBTENER TODOS LOS CARRITOS
    @GetMapping("/carrito")
    public ResponseEntity<?> obtenerCarritos(@RequestParam(value = "activo",required = false) Boolean activo){
        if(activo != null) {
            return new ResponseEntity<>(carritoRepository.findByActivoTrue(), HttpStatus.OK);
        }
        return new ResponseEntity<>(carritoRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/usuario/{id}/carrito/{idCarrito}")
    public ResponseEntity<?> obtenerCarritoPorIdCarrito(@PathVariable("id") Long id,
                                                        @PathVariable("idCarrito") Long idCarrito) {
        Carrito carrito = carritoRepository.findById(idCarrito).get();
        return ResponseEntity.ok(carrito);
    }
}