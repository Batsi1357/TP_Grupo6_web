package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Pregunta;
import com.example.tp_grupo6.repositories.PreguntaRepository;
import com.example.tp_grupo6.services.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PreguntaServiceImpl implements PreguntaService
{
    @Autowired
    private PreguntaRepository preguntaRepository;
    @Override
    public List<Pregunta> list() {
        return preguntaRepository.findAll();
    }

    @Override
    public void insert(Pregunta pregunta) {
        preguntaRepository.save(pregunta);
    }

    @Override
    public Pregunta listId(int id) {
        return preguntaRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        preguntaRepository.deleteById(id);
    }

    @Override
    public void update(Pregunta pregunta) {
        preguntaRepository.save(pregunta);
    }
}
