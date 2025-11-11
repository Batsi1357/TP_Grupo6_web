package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Evaluacion;

import java.time.LocalDate;
import java.util.List;

public interface EvaluacionService
{
    void update(Evaluacion evaluacion);
    List<Evaluacion> list();
    void insert(Evaluacion evaluacion);
    List<Evaluacion> buscarPorTitulo(String Titulo);
    Evaluacion listId(int id);
    void delete(int id);
    List<Evaluacion> listarPorFechaInicio(LocalDate FechaInicio);
    List<Evaluacion> listarPorDuracionMaxima(float maxDuracion);
    List<Evaluacion> listarPorDuracionMinima(float minDuracion);
}
