package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.RolDto;
import com.example.tp_grupo6.entities.Rol;
import com.example.tp_grupo6.services.RolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Rol")
@CrossOrigin("*")
public class RolController {
    @Autowired
    private RolService rolService;

    // ----------- READ: LISTAR TODOS -----------
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping
    public List<RolDto> listar() {
        return rolService.list().stream().map(rol -> {
            ModelMapper m = new ModelMapper();
            return m.map(rol, RolDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/insert")
    public ResponseEntity<Rol> add(@RequestBody Rol rol) {
        if (rol == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        rolService.insert(rol);
        return new ResponseEntity<>(rol, HttpStatus.CREATED);
    }
    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Rol rol = rolService.listId(id);
        if (rol == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un rol con ID: " + id);
        }
        return ResponseEntity.ok(rol);
    }

    // ----------- UPDATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Rol request) {
        Rol existente = rolService.listId(request.getIdRol());
        if (existente == null) {
            return new ResponseEntity<>("No existe un rol con ID: " + request.getIdRol(), HttpStatus.NOT_FOUND);
        }

        rolService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Rol rol = rolService.listId(id);
        if (rol == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un rol con ID: " + id);
        }
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
