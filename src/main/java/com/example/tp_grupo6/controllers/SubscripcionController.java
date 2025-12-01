package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.SubscripcionDto;
import com.example.tp_grupo6.entities.Clase;
import com.example.tp_grupo6.entities.Subscripcion;
import com.example.tp_grupo6.services.ClaseService;
import com.example.tp_grupo6.services.SubscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Subscripcion")
@CrossOrigin("*")
public class SubscripcionController {
    @Autowired
    private SubscripcionService subscripcionService;

    @Autowired
    private ClaseService claseService;

    // ----------- READ: LISTAR TODAS -----------
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping
    public List<SubscripcionDto> listar() {
        return subscripcionService.list()
                .stream()
                .map(sub -> {
                    SubscripcionDto dto = new SubscripcionDto();
                    dto.setIdSubscripcion(sub.getIdSubscripcion());
                    dto.setNombre(sub.getNombre());
                    dto.setDescripcion(sub.getDescripcion());
                    dto.setPrecio(sub.getPrecio());
                    if (sub.getClase() != null) {
                        dto.setClaseid(sub.getClase().getIdClase());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insert")
    public ResponseEntity<SubscripcionDto> add(@RequestBody SubscripcionDto dto) {

        Clase clase = claseService.listId(dto.getClaseid());
        if (clase == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Subscripcion sub = new Subscripcion();
        sub.setNombre(dto.getNombre());
        sub.setDescripcion(dto.getDescripcion());
        sub.setPrecio(dto.getPrecio());
        sub.setClase(clase);

        Subscripcion guardada = subscripcionService.insert(sub);

        SubscripcionDto respuesta = new SubscripcionDto();
        respuesta.setIdSubscripcion(guardada.getIdSubscripcion());
        respuesta.setNombre(guardada.getNombre());
        respuesta.setDescripcion(guardada.getDescripcion());
        respuesta.setPrecio(guardada.getPrecio());
        if (guardada.getClase() != null) {
            respuesta.setClaseid(guardada.getClase().getIdClase());
        }

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Subscripcion sub = subscripcionService.buscar(id);
        if (sub == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una suscripción con ID: " + id);
        }

        SubscripcionDto dto = new SubscripcionDto();
        dto.setIdSubscripcion(sub.getIdSubscripcion());
        dto.setNombre(sub.getNombre());
        dto.setDescripcion(sub.getDescripcion());
        dto.setPrecio(sub.getPrecio());
        if (sub.getClase() != null) {
            dto.setClaseid(sub.getClase().getIdClase());
        }

        return ResponseEntity.ok(dto);
    }

    // ----------- UPDATE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody SubscripcionDto dto) {
        Subscripcion existente = subscripcionService.buscar(dto.getIdSubscripcion());
        if (existente == null) {
            return new ResponseEntity<>("No existe una suscripción con ID: " + dto.getIdSubscripcion(),
                    HttpStatus.NOT_FOUND);
        }

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrecio(dto.getPrecio());

        if (dto.getClaseid() != null) {
            Clase clase = claseService.listId(dto.getClaseid());
            if (clase == null) {
                return new ResponseEntity<>("No existe una clase con ID: " + dto.getClaseid(),
                        HttpStatus.NOT_FOUND);
            }
            existente.setClase(clase);
        }

        subscripcionService.update(existente);

        SubscripcionDto respuesta = new SubscripcionDto();
        respuesta.setIdSubscripcion(existente.getIdSubscripcion());
        respuesta.setNombre(existente.getNombre());
        respuesta.setDescripcion(existente.getDescripcion());
        respuesta.setPrecio(existente.getPrecio());
        if (existente.getClase() != null) {
            respuesta.setClaseid(existente.getClase().getIdClase());
        }

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Subscripcion sub = subscripcionService.buscar(id);
        if (sub == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una suscripción con ID: " + id);
        }
        subscripcionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
