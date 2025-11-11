package com.example.tp_grupo6.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ApunteDto
{
    private int idApunte;
    private String Contenido;
    private LocalDate FechaCreacion;
    private Integer UnidadId;
    private Integer ClienteId;
}
