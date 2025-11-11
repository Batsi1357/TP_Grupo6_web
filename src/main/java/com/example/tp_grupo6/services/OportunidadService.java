package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Oportunidad;

import java.time.LocalDate;
import java.util.List;

public interface OportunidadService
{
    void update(Oportunidad oportunidad);
    List<Oportunidad> list();
    void insert(Oportunidad oportunidad);
    List<Oportunidad> buscarPorOportunidad(String Intento);
    Oportunidad listId(int id);
    void delete(int id);
    List<Oportunidad> listarPorFechaInicio(LocalDate FechaIntento);
}
