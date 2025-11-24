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
    private String titulo;
    private String descripcion;
    private int nivel;
    private String categoria;
    private int duracion;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "unidad", fetch = FetchType.EAGER )
    private List<Clase> clasesid;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "unidad_evaluacion", fetch = FetchType.EAGER )
    private List<Evaluacion> evaluaciones;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente_unidad;
}
