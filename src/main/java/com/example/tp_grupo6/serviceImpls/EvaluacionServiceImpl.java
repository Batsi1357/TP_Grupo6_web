package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Evaluacion;
import com.example.tp_grupo6.repositories.EvaluacionRepository;
import com.example.tp_grupo6.services.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class EvaluacionServiceImpl implements EvaluacionService
{
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    @Override
    public List<Evaluacion> list() {
        return evaluacionRepository.findAll();
    }
    @Override
    public void update(Evaluacion evaluacion) {evaluacionRepository.save(evaluacion);}
    @Override
    public Evaluacion insert(Evaluacion e) {
        return evaluacionRepository.save(e);
    }
    @Override
    public List<Evaluacion> buscarPorTitulo(String Titulo) {
        return List.of();
    }

    @Override
    public Evaluacion listId(int id) {
        return evaluacionRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        evaluacionRepository.deleteById(id);
    }

    @Override
    public List<Evaluacion> findByDuracionMaxima(int duracionMax) {
        return evaluacionRepository.findByDuracionLessThanEqual(duracionMax);
    }

    @Override
    public List<Evaluacion> findByFechaInicioMayor(LocalDate fecha) {
        return evaluacionRepository.buscarPorFechaInicioSQL(fecha);
    }

    @Override
    public List<Evaluacion> findByTituloContieneSQL(String titulo) {
        return evaluacionRepository.buscarPorTituloSQL(titulo);
    }

    @Override
    public List<Evaluacion> findEntreFechas(LocalDate inicio, LocalDate fin) {
        return evaluacionRepository.buscarEntreFechasJPQL(inicio, fin);
    }}
