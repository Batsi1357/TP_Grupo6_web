package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Apunte;
import com.example.tp_grupo6.repositories.ApunteRepository;
import com.example.tp_grupo6.services.ApunteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class ApunteServiceImpl implements ApunteService
{
    @Autowired
    private ApunteRepository apunteRepository;
    @Override
    public List<Apunte> list() {
        return apunteRepository.findAll();
    }

    @Override
    public void delete(int id) {
        apunteRepository.deleteById(id);
    }

    @Override
    public Apunte listId(int id) {
        return apunteRepository.findById(id).orElse(null);
    }

    @Override
    public void insert(Apunte apunte) {
        apunteRepository.save(apunte);
    }

    @Override
    public void update(Apunte apunte) {
        apunteRepository.save(apunte);
    }

    @Override
    public List<Apunte> listarPorFecha(LocalDate FechaCreacion) {
        return List.of();
    }
}
