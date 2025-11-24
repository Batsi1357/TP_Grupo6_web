package com.example.tp_grupo6.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EvaluacionDto
{
    private int idEvaluacion;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private int duracion;
    private Integer unidadid;
}
