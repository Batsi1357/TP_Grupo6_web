package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Evaluacion;

import java.time.LocalDate;
import java.util.List;

public interface EvaluacionService
{
    void update(Evaluacion e);
    List<Evaluacion> list();
    Evaluacion insert(Evaluacion evaluacion);
    List<Evaluacion> buscarPorTitulo(String Titulo);
    Evaluacion listId(int id);
    void delete(int id);
    // Querys
    List<Evaluacion> findByDuracionMaxima(int duracionMax);
    List<Evaluacion> findByFechaInicioMayor(LocalDate fecha);
    List<Evaluacion> findByTituloContieneSQL(String titulo);
    List<Evaluacion> findEntreFechas(LocalDate inicio, LocalDate fin);
}
