package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name="apuntes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apunte
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idApunte;
    private String Contenido;
    private LocalDate FechaCreacion;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "unidad_id")
    private Unidad unidad_apunte;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente_apunte;
}
