package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Unidad;

import java.util.List;
import java.util.Optional;

public interface UnidadService
{
    public List<Unidad> list();
    public void insert(Unidad unidad);
    public Unidad listId(int id);
    Optional<Unidad> findById(Integer id);
    List<Unidad> findByCategoria(String categoria);
    List<Unidad> findByNivel(String nivel);
    public void update(Unidad unidad);
    public void delete(int id);
}
