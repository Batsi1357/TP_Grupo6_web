package com.example.tp_grupo6.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EvaluacionDto
{
    private int idEvaluacion;
    private String Titulo;
    private String Descripcion;
    private LocalDate FechaInicio;
    private int Duracion;

}
