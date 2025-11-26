package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.RespuestaDto;
import com.example.tp_grupo6.entities.Pregunta;
import com.example.tp_grupo6.entities.Respuesta;
import com.example.tp_grupo6.services.PreguntaService;
import com.example.tp_grupo6.services.RespuestaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Respuesta")
@CrossOrigin("*")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private PreguntaService preguntaService;

    // ----------- LISTAR TODOS -----------
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping
    public List<RespuestaDto> listar() {
        return respuestaService.list().stream().map(respuesta -> {
            ModelMapper m = new ModelMapper();
            RespuestaDto dto = m.map(respuesta, RespuestaDto.class);
            if (respuesta.getPreguntaid() != null) {
                dto.setPreguntaId(respuesta.getPreguntaid().getIdPregunta());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    // ----------- LISTAR POR ID -----------
    @PreAuthorize("hasAnyRole('Admin','Estudiante')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Respuesta respuesta = respuestaService.listId(id);
        if (respuesta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una respuesta con ID: " + id);
        }

        ModelMapper m = new ModelMapper();
        RespuestaDto dto = m.map(respuesta, RespuestaDto.class);
        if (respuesta.getPreguntaid() != null) {
            dto.setPreguntaId(respuesta.getPreguntaid().getIdPregunta());
        }

        return ResponseEntity.ok(dto);
    }

    // ----------- INSERT -----------
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/insert")
    public ResponseEntity<?> add(@RequestBody RespuestaDto request) {

        if (request == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        if (request.getPreguntaId() == null) {
            return new ResponseEntity<>("Debe enviar un PreguntaId para la respuesta",
                    HttpStatus.BAD_REQUEST);
        }

        Pregunta pregunta = preguntaService.listId(request.getPreguntaId());
        if (pregunta == null) {
            return new ResponseEntity<>("No existe una pregunta con ID: " + request.getPreguntaId(),
                    HttpStatus.BAD_REQUEST);
        }

        Respuesta respuesta = new Respuesta();
        respuesta.setTexto(request.getTexto());
        respuesta.setRespuesta(request.getRespuesta());
        respuesta.setPreguntaid(pregunta);

        respuestaService.insert(respuesta);

        RespuestaDto response = new RespuestaDto();
        response.setIdRespuesta(respuesta.getIdRespuesta());
        response.setTexto(respuesta.getTexto());
        response.setRespuesta(respuesta.getRespuesta());
        response.setPreguntaId(pregunta.getIdPregunta());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ----------- UPDATE -----------
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody RespuestaDto request) {

        Respuesta existente = respuestaService.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una respuesta con ID: " + id);
        }

        existente.setTexto(request.getTexto());
        existente.setRespuesta(request.getRespuesta());

        if (request.getPreguntaId() != null) {
            Pregunta pregunta = preguntaService.listId(request.getPreguntaId());
            if (pregunta == null) {
                return new ResponseEntity<>("No existe una pregunta con ID: " + request.getPreguntaId(),
                        HttpStatus.BAD_REQUEST);
            }
            existente.setPreguntaid(pregunta);
        }

        respuestaService.update(existente);

        RespuestaDto response = new RespuestaDto();
        response.setIdRespuesta(existente.getIdRespuesta());
        response.setTexto(existente.getTexto());
        response.setRespuesta(existente.getRespuesta());
        if (existente.getRespuesta() != null) {
            response.setPreguntaId(existente.getPreguntaid().getIdPregunta());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Respuesta respuesta = respuestaService.listId(id);
        if (respuesta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una respuesta con ID: " + id);
        }
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}