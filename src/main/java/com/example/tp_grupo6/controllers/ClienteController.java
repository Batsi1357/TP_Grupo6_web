package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.ClienteDto;
import com.example.tp_grupo6.entities.Cliente;
import com.example.tp_grupo6.services.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Cliente")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // ----------- READ: LISTAR TODOS -----------
    @GetMapping
    public List<ClienteDto> listar() {
        return clienteService.list().stream().map(cliente -> {
            ModelMapper m = new ModelMapper();
            return m.map(cliente, ClienteDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PostMapping("/insert")
    public ResponseEntity<Cliente> add(@RequestBody Cliente cliente) {
        if (cliente == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        clienteService.insert(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
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
}