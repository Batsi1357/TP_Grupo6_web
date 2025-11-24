package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.ClaseDto;
import com.example.tp_grupo6.entities.Clase;
import com.example.tp_grupo6.entities.Unidad;
import com.example.tp_grupo6.services.ClaseService;
import com.example.tp_grupo6.services.UnidadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Clase")
@CrossOrigin("*")
public class ClaseController {
    @Autowired
    private ClaseService claseService;

    @Autowired
    private UnidadService unidadService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<ClaseDto> listar() {
        return claseService.list().stream().map(clase -> {
            ModelMapper m = new ModelMapper();
            return m.map(clase, ClaseDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<ClaseDto> add(@RequestBody ClaseDto dto) {

        // 1) Buscar la unidad
        Unidad unidad = unidadService.listId(dto.getUnidadId());
        if (unidad == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // 2) Armar la entity
        Clase clase = new Clase();
        clase.setClasePersonalizada(dto.getClasePersonalizada());
        clase.setUnidad(unidad);

        // 3) Guardar (haz que insert devuelva Clase)
        Clase guardada = claseService.insert(clase);

        // 4) Mapear a DTO para la respuesta
        ModelMapper m = new ModelMapper();
        ClaseDto respuesta = m.map(guardada, ClaseDto.class);

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
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
