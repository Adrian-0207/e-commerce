package com.informatorio.ecommerce.repository;

import com.informatorio.ecommerce.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    List<Usuario> findByFechaDeCreacionAfter(LocalDateTime dateTime);

    List<Usuario> findByCiudadContaining(String ciudad);
}
