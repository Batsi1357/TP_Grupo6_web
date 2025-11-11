package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="evaluaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvaluacion;
    private String Titulo;
    private String Descripcion;
    private LocalDate FechaInicio;
    private int Duracion;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "evaluacion_preguntas", fetch = FetchType.EAGER )
    private List<Pregunta> preguntas;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "evaluacion_oportunidades", fetch = FetchType.EAGER )
    private List<Oportunidad> oportunidades;
}
