package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Entity
@Table(name = "orden_subscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenSubscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrdenSubscripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente_ordenSubscripcionid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "subscripcion_id")
    private Subscripcion subscripcionid;
}
