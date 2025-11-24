package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Unidad;
import com.example.tp_grupo6.repositories.UnidadRepository;
import com.example.tp_grupo6.services.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UnidadServiceImpl implements UnidadService
{
    @Autowired
    private UnidadRepository unidadRepository;
    @Override
    public List<Unidad> list() {
        return unidadRepository.findAll();
    }
    @Override
    public void insert(Unidad unidad) {
        unidadRepository.save(unidad);
    }
    @Override
    public Unidad listId(int id) {
        return unidadRepository.findById(id).orElse(null);
    }
    @Override
    public void update(Unidad unidad) {
        unidadRepository.save(unidad);
    }
    @Override
    public void delete(int id) {
        unidadRepository.deleteById(id);
    }

    @Override
    public List<Unidad> findByCategoria(String categoria) {
        return unidadRepository.findByCategoria(categoria);
    }
    @Override
    public List<Unidad> findByNivelMayorIgual(int nivel) {
        return unidadRepository.findByNivelGreaterThanEqual(nivel);
    }
    @Override
    public List<Unidad> findByDuracionMinima(int minDuracion) {
        return unidadRepository.buscarPorDuracionSQL(minDuracion);
    }
    @Override
    public List<Unidad> findByTituloContiene(String texto) {
        return unidadRepository.buscarPorTituloJPQL(texto);
    }
}
