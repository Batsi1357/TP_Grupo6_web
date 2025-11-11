package com.example.tp_grupo6.dtos;
import lombok.Data;

@Data
public class PreguntaDto
{
    private int idPregunta;
    private String Enunciado;
    private String Tipo;
    private Float Puntaje;
    private Integer RespuestaId;
    private Integer EvaluacionId;
}
