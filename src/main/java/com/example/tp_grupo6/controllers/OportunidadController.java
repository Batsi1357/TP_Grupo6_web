package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.OportunidadDto;
import com.example.tp_grupo6.entities.Oportunidad;
import com.example.tp_grupo6.services.OportunidadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping
    public List<OportunidadDto> listar() {
        return oportunidadService.list().stream().map(oportunidad -> {
            ModelMapper m = new ModelMapper();
            return m.map(oportunidad, OportunidadDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/insert")
    public ResponseEntity<OportunidadDto> add(@RequestBody OportunidadDto dto) {
        ModelMapper m = new ModelMapper();
        Oportunidad entidad = m.map(dto, Oportunidad.class);

        // aquí tienes que setear la relación Evaluacion manualmente
        // por ejemplo:
        // Evaluacion eva = evaluacionService.listId(dto.getEvaluacionId());
        // entidad.setEvaluacion(eva);

        oportunidadService.insert(entidad);

        OportunidadDto respuesta = m.map(entidad, OportunidadDto.class);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Oportunidad oportunidad = oportunidadService.listId(id);
        if (oportunidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una oportunidad con ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        return ResponseEntity.ok(m.map(oportunidad, OportunidadDto.class));
    }
    // ----------- UPDATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody OportunidadDto dto) {
        Oportunidad existente = oportunidadService.listId(dto.getIdOportunidad());
        if (existente == null) {
            return new ResponseEntity<>("No existe una oportunidad con ID: " + dto.getIdOportunidad(),
                    HttpStatus.NOT_FOUND);
        }

        ModelMapper m = new ModelMapper();
        Oportunidad entidad = m.map(dto, Oportunidad.class);

        // igual: setear Evaluacion usando dto.getEvaluacionId()
        // Evaluacion eva = evaluacionService.listId(dto.getEvaluacionId());
        // entidad.setEvaluacion(eva);

        oportunidadService.update(entidad);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('Admin')")
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
