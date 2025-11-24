package com.example.tp_grupo6.dtos;
import lombok.Data;

@Data
public class SubscripcionDto
{
    private int idSubscripcion;
    private String Nombre;
    private String Descripcion;
    private Float Precio;
    private Integer claseid;
}
