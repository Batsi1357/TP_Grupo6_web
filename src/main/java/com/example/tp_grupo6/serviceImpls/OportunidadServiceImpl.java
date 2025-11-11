package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Oportunidad;
import com.example.tp_grupo6.repositories.OportunidadRepository;
import com.example.tp_grupo6.services.OportunidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class OportunidadServiceImpl implements OportunidadService
{
    @Autowired
    private OportunidadRepository oportunidadRepository;
    @Override
    public void update(Oportunidad oportunidad){ oportunidadRepository.save(oportunidad);
    }
    @Override
    public List<Oportunidad> list() {
        return oportunidadRepository.findAll();
    }

    @Override
    public void insert(Oportunidad oportunidad) {
        oportunidadRepository.save(oportunidad);
    }

    @Override
    public List<Oportunidad> buscarPorOportunidad(String Intento) {
        return List.of();
    }

    @Override
    public Oportunidad listId(int id) {
        return oportunidadRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        oportunidadRepository.deleteById(id);
    }

    @Override
    public List<Oportunidad> listarPorFechaInicio(LocalDate FechaIntento) {
        return List.of();
    }
}
