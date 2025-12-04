package com.example.tp_grupo6.dtos;
import lombok.Data;

@Data
public class UsuarioDto
{
    private Integer idUsuario;
    private String username;
    private String password;
    private String activo;
    private Integer RolId;
    private String rol; // Para enviar/recibir el nombre del rol como string
}
