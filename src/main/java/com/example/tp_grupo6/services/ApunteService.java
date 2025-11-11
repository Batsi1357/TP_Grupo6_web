package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Apunte;

import java.time.LocalDate;
import java.util.List;

public interface ApunteService
{
    List<Apunte> list();
    void delete(int id);
    Apunte listId(int id);
    void insert(Apunte apunte);
    void update(Apunte apunte);
    List<Apunte> listarPorFecha(LocalDate FechaCreacion);
}
