package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.PreguntaDto;
import com.example.tp_grupo6.entities.Evaluacion;
import com.example.tp_grupo6.entities.Pregunta;
import com.example.tp_grupo6.services.PreguntaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping
    public List<PreguntaDto> listar() {
        return preguntaService.list().stream().map(pregunta -> {
            ModelMapper m = new ModelMapper();
            return m.map(pregunta, PreguntaDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/insert")
    public ResponseEntity<PreguntaDto> add(@RequestBody PreguntaDto dto) {

        ModelMapper m = new ModelMapper();
        Pregunta pregunta = m.map(dto, Pregunta.class);

        // aquí conectas el EvaluacionId con la entidad Evaluacion
        Evaluacion eval = new Evaluacion();
        eval.setIdEvaluacion(dto.getEvaluacionId());
        pregunta.setEvaluacion_preguntasid(eval);

        preguntaService.insert(pregunta);

        PreguntaDto respuesta = m.map(pregunta, PreguntaDto.class);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
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
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody PreguntaDto dto) {
        Pregunta existente = preguntaService.listId(dto.getIdPregunta());
        if (existente == null) {
            return new ResponseEntity<>("No existe una pregunta con ID: " + dto.getIdPregunta(), HttpStatus.NOT_FOUND);
        }

        ModelMapper m = new ModelMapper();
        Pregunta pregunta = m.map(dto, Pregunta.class);

        Evaluacion eval = new Evaluacion();
        eval.setIdEvaluacion(dto.getEvaluacionId());
        pregunta.setEvaluacion_preguntasid(eval);

        preguntaService.update(pregunta);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('Admin')")
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
