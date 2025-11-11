package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.OrdenSubscripcion;

import java.time.LocalDate;
import java.util.List;

public interface OrdenSubscripcionService
{
    public List<OrdenSubscripcion> list();
    public void insert(OrdenSubscripcion  ordenSubscripcion);
    public OrdenSubscripcion listId(int id);
    public void delete(int id);
    public void update(OrdenSubscripcion  ordenSubscripcion);
    List<OrdenSubscripcion> listarPorRangoDeFechas(LocalDate FechaInicio, LocalDate FechaFin);
    List<OrdenSubscripcion> listarPorCliente(int idCliente);
}
