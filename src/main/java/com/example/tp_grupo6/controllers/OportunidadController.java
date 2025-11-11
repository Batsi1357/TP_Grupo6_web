package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.OportunidadDto;
import com.example.tp_grupo6.entities.Oportunidad;
import com.example.tp_grupo6.services.OportunidadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Oportunidad")
@CrossOrigin("*")

public class OportunidadController {
    @Autowired
    private OportunidadService oportunidadService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<OportunidadDto> listar() {
        return oportunidadService.list().stream().map(oportunidad -> {
            ModelMapper m = new ModelMapper();
            return m.map(oportunidad, OportunidadDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Oportunidad> add(@RequestBody Oportunidad oportunidad) {
        if (oportunidad == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        oportunidadService.insert(oportunidad);
        return new ResponseEntity<>(oportunidad, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Oportunidad oportunidad = oportunidadService.listId(id);
        if (oportunidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una oportunidad con ID: " + id);
        }
        return ResponseEntity.ok(oportunidad);
    }

    // ----------- UPDATE -----------
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Oportunidad request) {
        Oportunidad existente = oportunidadService.listId(request.getIdOportunidad());
        if (existente == null) {
            return new ResponseEntity<>("No existe una oportunidad con ID: " + request.getIdOportunidad(), HttpStatus.NOT_FOUND);
        }

        oportunidadService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Oportunidad oportunidad = oportunidadService.listId(id);
        if (oportunidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una oportunidad con ID: " + id);
        }
        oportunidadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
