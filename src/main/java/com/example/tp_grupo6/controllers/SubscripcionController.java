package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.SubscripcionDto;
import com.example.tp_grupo6.entities.Subscripcion;
import com.example.tp_grupo6.services.SubscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Subscripcion")
@CrossOrigin("*")
public class SubscripcionController {
    @Autowired
    private SubscripcionService subscripcionService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<SubscripcionDto> listar() {
        return subscripcionService.list().stream().map(sub -> {
            ModelMapper m = new ModelMapper();
            return m.map(sub, SubscripcionDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Subscripcion> add(@RequestBody Subscripcion subscripcion) {
        if (subscripcion == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        subscripcionService.insert(subscripcion);
        return new ResponseEntity<>(subscripcion, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Subscripcion subscripcion = subscripcionService.buscar(id);
        if (subscripcion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una suscripción con ID: " + id);
        }
        return ResponseEntity.ok(subscripcion);
    }

    // ----------- UPDATE -----------
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Subscripcion request) {
        Subscripcion existente = subscripcionService.buscar(request.getIdSubscripcion());
        if (existente == null) {
            return new ResponseEntity<>("No existe una suscripción con ID: " + request.getIdSubscripcion(), HttpStatus.NOT_FOUND);
        }

        subscripcionService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Subscripcion subscripcion = subscripcionService.buscar(id);
        if (subscripcion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una suscripción con ID: " + id);
        }
        subscripcionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
