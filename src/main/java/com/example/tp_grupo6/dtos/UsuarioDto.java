package com.example.tp_grupo6.dtos;
import lombok.Data;

@Data
public class UsuarioDto
{
    private int idUsuario;
    private String Username;
    private String Password;
    private String activo;
    private Integer RolId;
}
