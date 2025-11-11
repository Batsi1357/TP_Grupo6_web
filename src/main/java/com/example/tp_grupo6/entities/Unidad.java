package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="unidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unidad
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUnidad;
    private String Titulo;
    private String Descripcion;
    private int Nivel;
    private String Categoria;
    private int Duracion;

    @JsonIgnore
    @OneToMany(mappedBy = "unidad_clase", fetch = FetchType.EAGER )
    @ToString.Exclude
    private List<Clase> clases;
    @JsonIgnore
    @OneToMany(mappedBy = "unidad_apunte", fetch = FetchType.EAGER )
    @ToString.Exclude
    private List<Apunte> apuntes;

}
