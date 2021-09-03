package com.informatorio.ecommerce.controller;

import com.informatorio.ecommerce.domain.Usuario;
import com.informatorio.ecommerce.repository.UsuarioRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void borrarUsuario(@PathVariable("id") Long id) {
        usuarioRepository.deleteById(id);
        //Usuario usuario = usuarioRepository.getById(id);
        //usuarioRepository.delete(usuario);
    }

    // NO puedo modificar el pasword pero si tengo que ponerlo en el json
    // de la request para que no me de error
    @PutMapping("/{id}")
    public Usuario modificarUsuario(@PathVariable("id") Long id,
                                    @Valid @RequestBody Usuario usuario) {
        Usuario usuarioModificado = usuarioRepository.getById(id);
        usuarioModificado.setNombre(usuario.getNombre());
        usuarioModificado.setApellido(usuario.getApellido());
        usuarioModificado.setCiudad(usuario.getCiudad());
        usuarioModificado.setProvincia(usuario.getProvincia());
        usuarioModificado.setPais(usuario.getPais());
        return usuarioRepository.save(usuarioModificado);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosUsuarios(
            @RequestParam(name = "fechaDeCreacion", required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDeCreacion,
            @RequestParam(name = "ciudad", required = false) String ciudad) {
        if(fechaDeCreacion != null) {
            return new ResponseEntity<>(usuarioRepository.findByFechaDeCreacionAfter
                    (fechaDeCreacion.atStartOfDay()), HttpStatus.OK);
        } else if (Objects.nonNull (ciudad)) {
            return new ResponseEntity<>(usuarioRepository.findByCiudadContaining(ciudad), HttpStatus.OK);
        }
        return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);
    }
}

