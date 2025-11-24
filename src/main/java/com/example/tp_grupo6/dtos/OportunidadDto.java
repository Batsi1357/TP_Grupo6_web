package com.example.tp_grupo6.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OportunidadDto
{
    private int idOportunidad;
    private int intento;
    private LocalDate fechaInicio;
    private Integer evaluacionId;
}
