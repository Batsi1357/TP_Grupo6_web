package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="respuestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRespuesta;
    private String Texto;
    private String PRespuesta;
    @JsonIgnore
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "respuesta", unique = true, nullable = false)
    private Pregunta pregunta;
}
