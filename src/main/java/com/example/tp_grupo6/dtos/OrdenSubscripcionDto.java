package com.example.tp_grupo6.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OrdenSubscripcionDto
{
    private int idOrdenSubscripcion;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer clienteId;
    private Integer SubscripcionId;
}
