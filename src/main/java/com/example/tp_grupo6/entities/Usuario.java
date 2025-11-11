package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    private String Username;
    private String Password;
    private String activo;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="rol_id")
    private Rol rol;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Cliente cliente_usuario;
}
