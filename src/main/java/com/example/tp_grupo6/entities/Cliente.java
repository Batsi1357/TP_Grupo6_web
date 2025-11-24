package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String Celular;
    private String email;
    private int edad;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_usuarioid", unique = true, nullable = false)
    private Usuario usuario;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente_ordenSubscripcionid", fetch = FetchType.LAZY)
    private List<OrdenSubscripcion> ordenSubscripcion;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente_unidad", fetch = FetchType.LAZY)
    private List<Unidad> unidades;

}
 