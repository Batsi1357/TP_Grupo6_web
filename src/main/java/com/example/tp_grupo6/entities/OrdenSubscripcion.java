package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Entity
@Table(name="ordenSubscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenSubscripcion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrdenSubscripcion;
    private String Estado;
    private LocalDate FechaInicio;
    private LocalDate FechaFin;
    @JsonIgnore
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente", unique = true, nullable = false)
    private Cliente cliente_ordenSubscripcion;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="subscripcion_id")
    private Subscripcion subscripcion;
    
}
