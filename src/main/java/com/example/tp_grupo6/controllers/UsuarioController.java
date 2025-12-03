package com.example.tp_grupo6.controllers;
import com.example.tp_grupo6.entities.Rol;
import com.example.tp_grupo6.services.RolService;
import com.example.tp_grupo6.dtos.UsuarioDto;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Usuario")
@CrossOrigin("*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    // ----------- READ: LISTAR TODOS -----------
    @GetMapping
    public List<UsuarioDto> listar() {
        return usuarioService.list().stream().map(usuario -> {
            ModelMapper m = new ModelMapper();
            UsuarioDto dto = m.map(usuario, UsuarioDto.class);
            if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
                dto.setRolId(usuario.getRoles().get(0).getIdRol());
            }
            return dto;
        }).collect(Collectors.toList());
    }
    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<?> add(@RequestBody UsuarioDto request) {

        if (request == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        // Buscar el rol por id (si viene)
        Rol rol = null;
        if (request.getRolId() != null) {
            rol = rolService.listId(request.getRolId());
            if (rol == null) {
                return new ResponseEntity<>("No existe un rol con ID: " + request.getRolId(),
                        HttpStatus.BAD_REQUEST);
            }
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword());
        usuario.setActivo(request.getActivo());

// IMPORTANTE: convertir Rol → List<Rol>
        if (rol != null) {
            usuario.setRoles(List.of(rol)); // o Arrays.asList(rol)
        }

        usuarioService.insert(usuario);

// respuesta
        UsuarioDto response = new UsuarioDto();
        response.setIdUsuario(usuario.getIdUsuario());
        response.setUsername(usuario.getUsername());
        response.setPassword(usuario.getPassword());
        response.setActivo(usuario.getActivo());
        response.setRolId(rol != null ? rol.getIdRol() : null);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Usuario usuario = usuarioService.listId(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con ID: " + id);
        }

        ModelMapper m = new ModelMapper();
        UsuarioDto dto = m.map(usuario, UsuarioDto.class);

        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            dto.setRolId(usuario.getRoles().get(0).getIdRol());
        }

        return ResponseEntity.ok(dto);
    }
    // ----------- UPDATE -----------
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Usuario request) {

        // Buscar usuario existente
        Usuario existente = usuarioService.listId(request.getIdUsuario());
        if (existente == null) {
            return new ResponseEntity<>(
                    "No existe un usuario con ID: " + request.getIdUsuario(),
                    HttpStatus.NOT_FOUND
            );
        }

        // Actualizar SOLO campos básicos (no tocamos los roles)
        existente.setUsername(request.getUsername());
        existente.setPassword(request.getPassword());
        existente.setActivo(request.getActivo());

        // Guardar cambios
        usuarioService.update(existente);

        // Puedes devolver el entity o si quieres un DTO sin rolId
        return new ResponseEntity<>(existente, HttpStatus.OK);
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

    // ----------- REGISTRO PÚBLICO (sin autenticación) -----------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDto request) {

        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return new ResponseEntity<>("Datos incompletos", HttpStatus.BAD_REQUEST);
        }

        // Verificar si el usuario ya existe
        Usuario existente = usuarioService.findByUsername(request.getUsername());
        if (existente != null) {
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        }

        // Determinar el rol (por defecto ESTUDIANTE si no viene)
        String rolNombre = request.getRol() != null ? request.getRol() : "ESTUDIANTE";

        // Buscar el rol por nombre
        Rol rol = rolService.findByNombre(rolNombre);
        if (rol == null) {
            return new ResponseEntity<>("Rol no válido: " + rolNombre, HttpStatus.BAD_REQUEST);
        }

        // Crear el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword()); // Asegúrate de encriptar en el service
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : "S");
        usuario.setRoles(List.of(rol));

        // Guardar el usuario
        usuarioService.insert(usuario);

        // Preparar respuesta (sin incluir la contraseña)
        UsuarioDto response = new UsuarioDto();
        response.setIdUsuario(usuario.getIdUsuario());
        response.setUsername(usuario.getUsername());
        response.setActivo(usuario.getActivo());
        response.setRol(rolNombre);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

