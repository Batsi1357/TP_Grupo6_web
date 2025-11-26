package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.UnidadDto;
import com.example.tp_grupo6.entities.Unidad;
import com.example.tp_grupo6.services.UnidadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping
    public List<UnidadDto> listar() {
        return unidadService.list().stream().map(unidad -> {
            ModelMapper m = new ModelMapper();
            return m.map(unidad, UnidadDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/insert")
    public ResponseEntity<Unidad> add(@RequestBody Unidad unidad) {
        if (unidad == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        unidadService.insert(unidad);
        return new ResponseEntity<>(unidad, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasRole('Estudiante')")
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
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Unidad request) {
        Unidad existente = unidadService.listId(request.getIdUnidad());
        if (existente == null) {
            return new ResponseEntity<>("No existe una unidad con ID: " + request.getIdUnidad(), HttpStatus.NOT_FOUND);
        }

        unidadService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('Admin')")
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

    // QM #1: por categoría exacta
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/buscar-categoria")
    public List<Unidad> buscarPorCategoria(@RequestParam String categoria) {
        return unidadService.findByCategoria(categoria);
    }

    // QM #2: por nivel >=
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/buscar-nivel")
    public List<Unidad> buscarPorNivelMayorIgual(@RequestParam int nivel) {
        return unidadService.findByNivelMayorIgual(nivel);
    }

    // SQL nativo #1: duración mínima
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/buscar-duracion")
    public List<Unidad> buscarPorDuracionMinima(@RequestParam int minDuracion) {
        return unidadService.findByDuracionMinima(minDuracion);
    }

    // JPQL #1: título contiene texto
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/buscar-titulo")
    public List<Unidad> buscarPorTitulo(@RequestParam String texto) {
        return unidadService.findByTituloContiene(texto);
    }

}
