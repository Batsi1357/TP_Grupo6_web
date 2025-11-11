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
    public void insert(Evaluacion evaluacion) {
        evaluacionRepository.save(evaluacion);
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
    public List<Evaluacion> listarPorFechaInicio(LocalDate FechaInicio) {
        return List.of();
    }

    @Override
    public List<Evaluacion> listarPorDuracionMaxima(float maxDuracion) {
        return List.of();
    }

    @Override
    public List<Evaluacion> listarPorDuracionMinima(float minDuracion) {
        return List.of();
    }
}
