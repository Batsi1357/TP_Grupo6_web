package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Respuesta;
import com.example.tp_grupo6.repositories.RespuestaRepository;
import com.example.tp_grupo6.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RespuestaServiceImpl implements RespuestaService
{
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Override
    public List<Respuesta> list() {
        return List.of();
    }

    @Override
    public void insert(Respuesta respuesta) {
        respuestaRepository.save(respuesta);
    }

    @Override
    public Respuesta listId(int id) {
        return respuestaRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        respuestaRepository.deleteById(id);
    }

    @Override
    public void update(Respuesta respuesta) {
        respuestaRepository.save(respuesta);
    }
}
