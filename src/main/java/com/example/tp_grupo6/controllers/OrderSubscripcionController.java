package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.OrdenSubscripcionDto;
import com.example.tp_grupo6.entities.OrdenSubscripcion;
import com.example.tp_grupo6.services.OrdenSubscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/OrderSubscripcion")
@CrossOrigin("*")
public class OrderSubscripcionController
{
    @Autowired
    private OrdenSubscripcionService ordenSubscripcionService;

    // ----------- READ: LISTAR TODAS -----------
    @GetMapping
    public List<OrdenSubscripcionDto> listar() {
        return ordenSubscripcionService.list().stream().map(orden -> {
            ModelMapper m = new ModelMapper();
            return m.map(orden, OrdenSubscripcionDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/insert")
    public ResponseEntity<OrdenSubscripcionDto> add(@RequestBody OrdenSubscripcionDto dto) {

        ModelMapper m = new ModelMapper();
        OrdenSubscripcion entidad = m.map(dto, OrdenSubscripcion.class);

        // aquí mapeas clienteId y SubscripcionId a las relaciones reales:
        // Cliente cliente = new Cliente();
        // cliente.setIdCliente(dto.getClienteId());
        // entidad.setCliente(cliente);
        //
        // Subscripcion subscripcion = new Subscripcion();
        // subscripcion.setIdSubscripcion(dto.getSubscripcionId());
        // entidad.setSubscripcion(subscripcion);

        ordenSubscripcionService.insert(entidad);

        OrdenSubscripcionDto respuesta = m.map(entidad, OrdenSubscripcionDto.class);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        OrdenSubscripcion ordenSubscripcion = ordenSubscripcionService.listId(id);
        if (ordenSubscripcion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una orden de suscripción con ID: " + id);
        }
        return ResponseEntity.ok(ordenSubscripcion);
    }

    // ----------- UPDATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody OrdenSubscripcion request) {
        OrdenSubscripcion existente = ordenSubscripcionService.listId(request.getIdOrdenSubscripcion());
        if (existente == null) {
            return new ResponseEntity<>("No existe una orden con ID: " + request.getIdOrdenSubscripcion(), HttpStatus.NOT_FOUND);
        }

        ordenSubscripcionService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        OrdenSubscripcion ordenSubscripcion = ordenSubscripcionService.listId(id);
        if (ordenSubscripcion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una orden de suscripción con ID: " + id);
        }
        ordenSubscripcionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
