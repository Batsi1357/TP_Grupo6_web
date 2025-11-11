package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Entity
@Table(name="oportunidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oportunidad
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOportunidad;
    private int Intento;
    private LocalDate FechaInicio;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="evaluacion_id")
    private Evaluacion evaluacion_oportunidades;

}
