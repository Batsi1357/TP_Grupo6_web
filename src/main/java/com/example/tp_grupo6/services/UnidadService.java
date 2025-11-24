package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Unidad;

import java.util.List;
import java.util.Optional;

public interface UnidadService
{
    public List<Unidad> list();
    public void insert(Unidad unidad);
    public Unidad listId(int id);
    public void update(Unidad unidad);
    public void delete(int id);
    // Métodos para las querys del repository
    List<Unidad> findByCategoria(String categoria);
    List<Unidad> findByNivelMayorIgual(int nivel);
    List<Unidad> findByDuracionMinima(int minDuracion);
    List<Unidad> findByTituloContiene(String texto);
}
