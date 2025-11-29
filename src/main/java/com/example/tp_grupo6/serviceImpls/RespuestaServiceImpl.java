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
        System.out.println("🔍 [RESPUESTA-SERVICE] list() llamado");
        List<Respuesta> items = respuestaRepository.findAll();
        System.out.println("📊 [RESPUESTA-SERVICE] Respuestas encontradas: " + (items != null ? items.size() : 0));
        if (items != null && !items.isEmpty()) {
            items.forEach(r -> System.out.println("  - ID: " + r.getIdRespuesta() + ", Texto: " + r.getTexto()));
        } else {
            System.out.println("⚠️ [RESPUESTA-SERVICE] Lista vacía o null");
        }
        return items;
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
