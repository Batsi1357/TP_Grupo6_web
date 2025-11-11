package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Clase;

import java.util.List;

public interface ClaseService
{
    public List<Clase> list();
    public void insert(Clase  clase);
    public Clase listId(int id);
    public void delete(int id);
    public void update(Clase  clase);
    List<Clase> listarPorClasePersonalizada(String ClasePersonalizada);
}
