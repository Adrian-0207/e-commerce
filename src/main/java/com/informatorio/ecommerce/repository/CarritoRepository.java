package com.informatorio.ecommerce.repository;

import com.informatorio.ecommerce.domain.Carrito;
import com.informatorio.ecommerce.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    List<Carrito> findByActivoTrue();
}
