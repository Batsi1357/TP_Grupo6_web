package com.example.tp_grupo6.controllers;
import com.example.tp_grupo6.dtos.EvaluacionDto;
import com.example.tp_grupo6.entities.Evaluacion;
import com.example.tp_grupo6.services.EvaluacionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Evaluacion")
@CrossOrigin("*")
public class EvaluacionController {
    @Autowired
    private EvaluacionService evaluacionService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<EvaluacionDto> listar() {
        return evaluacionService.list().stream().map(evaluacion -> {
            ModelMapper m = new ModelMapper();
            return m.map(evaluacion, EvaluacionDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Evaluacion> add(@RequestBody Evaluacion evaluacion) {
        if (evaluacion == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        evaluacionService.insert(evaluacion);
        return new ResponseEntity<>(evaluacion, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Evaluacion evaluacion = evaluacionService.listId(id);
        if (evaluacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una evaluación con ID: " + id);
        }
        return ResponseEntity.ok(evaluacion);
    }

    // ----------- UPDATE -----------
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Evaluacion request) {
        Evaluacion existente = evaluacionService.listId(request.getIdEvaluacion());
        if (existente == null) {
            return new ResponseEntity<>("No existe una evaluación con ID: " + request.getIdEvaluacion(), HttpStatus.NOT_FOUND);
        }

        evaluacionService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Evaluacion evaluacion = evaluacionService.listId(id);
        if (evaluacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una evaluación con ID: " + id);
        }
        evaluacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
