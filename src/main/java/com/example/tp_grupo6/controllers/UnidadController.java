package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.UnidadDto;
import com.example.tp_grupo6.entities.Unidad;
import com.example.tp_grupo6.services.UnidadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Unidad")
@CrossOrigin("*")
public class UnidadController
{
    @Autowired
    private UnidadService unidadService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<UnidadDto> listar() {
        return unidadService.list().stream().map(unidad -> {
            ModelMapper m = new ModelMapper();
            return m.map(unidad, UnidadDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Unidad> add(@RequestBody Unidad unidad) {
        if (unidad == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        unidadService.insert(unidad);
        return new ResponseEntity<>(unidad, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Unidad unidad = unidadService.listId(id);
        if (unidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una unidad con ID: " + id);
        }
        return ResponseEntity.ok(unidad);
    }

    // ----------- UPDATE -----------
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Unidad request) {
        Unidad existente = unidadService.listId(request.getIdUnidad());
        if (existente == null) {
            return new ResponseEntity<>("No existe una unidad con ID: " + request.getIdUnidad(), HttpStatus.NOT_FOUND);
        }

        unidadService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Unidad unidad = unidadService.listId(id);
        if (unidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una unidad con ID: " + id);
        }
        unidadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
