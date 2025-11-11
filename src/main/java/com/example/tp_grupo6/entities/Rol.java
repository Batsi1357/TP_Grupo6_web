package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;
    private String TipoRol;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "rol", fetch = FetchType.EAGER)
    private List<Usuario> usuarios;

}
