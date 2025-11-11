package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClase;
    private String ClasePersonalizada;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "unidad_id")
    private Unidad unidad_clase;

}
