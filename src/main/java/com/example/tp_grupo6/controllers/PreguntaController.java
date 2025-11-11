package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.PreguntaDto;
import com.example.tp_grupo6.entities.Pregunta;
import com.example.tp_grupo6.services.PreguntaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Pregunta")
@CrossOrigin("*")
public class PreguntaController
{
    @Autowired
    private PreguntaService preguntaService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<PreguntaDto> listar() {
        return preguntaService.list().stream().map(pregunta -> {
            ModelMapper m = new ModelMapper();
            return m.map(pregunta, PreguntaDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Pregunta> add(@RequestBody Pregunta pregunta) {
        if (pregunta == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        preguntaService.insert(pregunta);
        return new ResponseEntity<>(pregunta, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Pregunta pregunta = preguntaService.listId(id);
        if (pregunta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una pregunta con ID: " + id);
        }
        return ResponseEntity.ok(pregunta);
    }

    // ----------- UPDATE -----------
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Pregunta request) {
        Pregunta existente = preguntaService.listId(request.getIdPregunta());
        if (existente == null) {
            return new ResponseEntity<>("No existe una pregunta con ID: " + request.getIdPregunta(), HttpStatus.NOT_FOUND);
        }

        preguntaService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Pregunta pregunta = preguntaService.listId(id);
        if (pregunta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una pregunta con ID: " + id);
        }
        preguntaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
