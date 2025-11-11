package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.RespuestaDto;
import com.example.tp_grupo6.entities.Respuesta;
import com.example.tp_grupo6.services.RespuestaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Respuesta")
@CrossOrigin("*")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<RespuestaDto> listar() {
        return respuestaService.list().stream().map(respuesta -> {
            ModelMapper m = new ModelMapper();
            return m.map(respuesta, RespuestaDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Respuesta> add(@RequestBody Respuesta respuesta) {
        if (respuesta == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        respuestaService.insert(respuesta);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Respuesta respuesta = respuestaService.listId(id);
        if (respuesta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una respuesta con ID: " + id);
        }
        return ResponseEntity.ok(respuesta);
    }

    // ----------- UPDATE -----------
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Respuesta request) {
        Respuesta existente = respuestaService.listId(request.getIdRespuesta());
        if (existente == null) {
            return new ResponseEntity<>("No existe una respuesta con ID: " + request.getIdRespuesta(), HttpStatus.NOT_FOUND);
        }

        respuestaService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Respuesta respuesta = respuestaService.listId(id);
        if (respuesta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una respuesta con ID: " + id);
        }
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
