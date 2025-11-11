package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.ClaseDto;
import com.example.tp_grupo6.entities.Clase;
import com.example.tp_grupo6.services.ClaseService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Clase")
@CrossOrigin("*")
public class ClaseController {
    private ClaseService claseService;

    @GetMapping
    public List<ClaseDto> listar() {
        return claseService.list().stream().map(clase -> {
            ModelMapper m = new ModelMapper();
            return m.map(clase, ClaseDto.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/insert")
    public ResponseEntity<Clase> add(@RequestBody Clase clase) {
        if (clase == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        claseService.insert(clase);
        return new ResponseEntity<>(clase, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Clase clase = claseService.listId(id);
        if (clase == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una clase con ID: " + id);
        }
        return ResponseEntity.ok(clase);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Clase request) {

        Clase existente = claseService.listId(request.getIdClase());
        if (existente == null) {
            return new ResponseEntity<>("No existe una clase con ID: " + request.getIdClase(), HttpStatus.NOT_FOUND);
        }
        claseService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Clase clase = claseService.listId(id);
        if (clase == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una clase con ID: " + id);
        }
        claseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
