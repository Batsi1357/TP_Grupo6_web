package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="preguntas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPregunta;
    private String Enunciado;
    private String Tipo;
    private Float Puntaje;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evaluacion_id")
    private Evaluacion evaluacion_preguntasid;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "preguntaid", fetch = FetchType.LAZY)
    private List<Respuesta> respuestaid;

}
