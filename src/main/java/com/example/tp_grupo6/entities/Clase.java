package com.example.tp_grupo6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
    private Unidad unidad;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(
            mappedBy = "clase",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Subscripcion> subscripciones;

}
