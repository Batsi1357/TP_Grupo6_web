package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.UsuarioDto;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Usuario")
@CrossOrigin("*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // ----------- READ: LISTAR TODOS -----------
    @GetMapping
    public List<UsuarioDto> listar() {
        return usuarioService.list().stream().map(usuario -> {
            ModelMapper m = new ModelMapper();
            return m.map(usuario, UsuarioDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Usuario   > add(@RequestBody Usuario usuario) {
        if (usuario == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        usuarioService.insert(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Usuario usuario = usuarioService.listId(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con ID: " + id);
        }
        return ResponseEntity.ok(usuario);
    }

    // ----------- UPDATE -----------
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Usuario request) {
        Usuario existente = usuarioService.listId(request.getIdUsuario());
        if (existente == null) {
            return new ResponseEntity<>("No existe un usuario con ID: " + request.getIdUsuario(), HttpStatus.NOT_FOUND);
        }

        usuarioService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Usuario usuario = usuarioService.listId(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con ID: " + id);
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

