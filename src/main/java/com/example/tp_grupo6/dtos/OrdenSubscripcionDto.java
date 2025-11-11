package com.example.tp_grupo6.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OrdenSubscripcionDto
{
    private int idOrdenSubscripcion;
    private String Estado;
    private LocalDate FechaInicio;
    private LocalDate FechaFin;
    private Integer ClienteId;
    private Integer SubscripcionId;
}
