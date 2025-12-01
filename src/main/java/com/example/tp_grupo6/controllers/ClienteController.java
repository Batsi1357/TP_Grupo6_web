package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.ClienteDto;
import com.example.tp_grupo6.entities.Cliente;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.services.ClienteService;
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
@RequestMapping("/Cliente")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    // ----------- READ: LISTAR TODOS -----------
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping
    public List<ClienteDto> listar() {
        return clienteService.list().stream().map(cliente -> {
            ModelMapper m = new ModelMapper();
            return m.map(cliente, ClienteDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insert")
    public ResponseEntity<?> add(@RequestBody ClienteDto dto) {
        if (dto == null) {
            return new ResponseEntity<>("El cuerpo de la solicitud no puede ser nulo",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        // Buscar el usuario por id (si se recibe el usuarioId en el DTO)
        Usuario usuario = null;
        if (dto.getUsuarioId() != null) {
            usuario = usuarioService.listId(dto.getUsuarioId());  // Buscar el usuario existente
            if (usuario == null) {
                return new ResponseEntity<>("No existe un usuario con ID: " + dto.getUsuarioId(),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            // Si no se recibe un usuarioId, error
            return new ResponseEntity<>("El usuario es obligatorio", HttpStatus.BAD_REQUEST);
        }

        // Crear cliente y asociar el usuario
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setEdad(dto.getEdad());
        cliente.setCelular(dto.getCelular());
        cliente.setDireccion(dto.getDireccion());
        cliente.setUsuario(usuario);

        // Guardar el cliente
        clienteService.insert(cliente);

        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }
    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Cliente cliente = clienteService.listId(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un cliente con ID: " + id);
        }
        return ResponseEntity.ok(cliente);
    }

    // ----------- UPDATE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Cliente request) {
        Cliente existente = clienteService.listId(request.getIdCliente());
        if (existente == null) {
            return new ResponseEntity<>("No existe un cliente con ID: " + request.getIdCliente(), HttpStatus.NOT_FOUND);
        }

        clienteService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Cliente cliente = clienteService.listId(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un cliente con ID: " + id);
        }
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Endpoints de las 4 querys ----------

    // QM #4: por apellido exacto
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-email")
    public List<Cliente> buscarporEmail(@RequestParam String email) {
        return clienteService.findByEmail(email);
    }

    // SQL nativo #4: dominio de email (ej: "@gmail.com")
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-dominio")
    public List<Cliente> buscarPorDominio(@RequestParam String dominio) {
        return clienteService.findByDominioEmailSQL(dominio);
    }

    // JPQL #3: edad >= edadMin
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-edad")
    public List<Cliente> buscarPorEdadMinima(@RequestParam int edadMin) {
        return clienteService.findByEdadMinimaJPQL(edadMin);
    }

    // JPQL #4: nombre o apellido contiene texto
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-nombre-apellido")
    public List<Cliente> buscarPorNombreOApellido(@RequestParam String texto) {
        return clienteService.findByNombreOApellidoContiene(texto);
    }
}