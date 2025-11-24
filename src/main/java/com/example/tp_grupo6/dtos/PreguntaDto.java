package com.example.tp_grupo6.dtos;
import lombok.Data;

@Data
public class PreguntaDto
{
    private int idPregunta;
    private String enunciado;
    private String tipo;
    private Float puntaje;
    private Integer evaluacionId;
}
