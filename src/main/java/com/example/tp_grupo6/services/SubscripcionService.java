package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Subscripcion;

import java.util.List;

public interface SubscripcionService
{
    public List<Subscripcion> list();
    public void insert(Subscripcion suscripcion);
    public Subscripcion buscar(int id);
    public void delete(int id);
    public void update(Subscripcion suscripcion);
    public List<String[]> quantitytypebySuscripciones();
}
