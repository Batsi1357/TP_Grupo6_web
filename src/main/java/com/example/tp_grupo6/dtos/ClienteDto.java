package com.example.tp_grupo6.dtos;
import lombok.Data;

@Data
public class ClienteDto
{
    private int idCliente;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String Celular;
    private String email;
    private Integer edad;
    private Integer UsuarioId;
}
