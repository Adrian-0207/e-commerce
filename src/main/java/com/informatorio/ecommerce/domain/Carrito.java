package com.informatorio.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore //Ignora el usuario en el carrito. No lo devuelve en el JSON del post del carrito
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @NotBlank
    private String dispositivo;

    /*
    @Transient   //NO se guarda en la tabla. No es persistente
    private BigDecimal total;
    */

    @Transient
    private BigDecimal total;

    @CreationTimestamp
    private LocalDateTime fechaDeCreacion;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaDeCarrito> lineasDeCarrito = new ArrayList<>();

    private Boolean activo = true;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

/*
    public List<LineaDeCarrito> getLineasDeCarrito() {
        return lineasDeCarrito;
    }

 */




    public void setLineasDeCarrito(List<LineaDeCarrito> lineasDeCarrito) {
        this.lineasDeCarrito = lineasDeCarrito;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (LineaDeCarrito lineaDeCarrito: this.lineasDeCarrito) {
            total = total.add(lineaDeCarrito.getSubTotal());
        }
        return total;
    }




    /*
    public double getTotal() {
        for(LineaDeCarrito lineaCarrito:this.getLineasDeCarrito()){
            var subT = lineaCarrito.getSubTotal().doubleValue();
            this.total+=subT;
        }
        return this.total;
    }
*/


    public void agregarLineDeCarrito(LineaDeCarrito lineaDeCarrito) {
        lineasDeCarrito.add(lineaDeCarrito);
        lineaDeCarrito.setCarrito(this);
    }

    public Boolean getactivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}