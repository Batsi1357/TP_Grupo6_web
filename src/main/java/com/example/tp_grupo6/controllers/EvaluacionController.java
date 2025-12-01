package com.example.tp_grupo6.controllers;
import com.example.tp_grupo6.dtos.EvaluacionDto;
import com.example.tp_grupo6.entities.Evaluacion;
import com.example.tp_grupo6.entities.Unidad;
import com.example.tp_grupo6.services.EvaluacionService;
import com.example.tp_grupo6.services.UnidadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Evaluacion")
@CrossOrigin("*")
public class EvaluacionController {
    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private UnidadService unidadService;
    // ----------- READ: LISTAR TODAS -----------
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping
    public List<EvaluacionDto> listar() {
        return evaluacionService.list().stream().map(evaluacion -> {
            ModelMapper m = new ModelMapper();
            return m.map(evaluacion, EvaluacionDto.class);
        }).collect(Collectors.toList());
    }

    // ----------- CREATE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/insert")
    public ResponseEntity<EvaluacionDto> add(@RequestBody EvaluacionDto dto) {

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTitulo(dto.getTitulo());
        evaluacion.setDescripcion(dto.getDescripcion());
        evaluacion.setFechaInicio(dto.getFechaInicio());
        evaluacion.setDuracion(dto.getDuracion());

        // buscar la unidad por id y asignarla
        Unidad unidad = unidadService.listId(dto.getUnidadid());
        evaluacion.setUnidad_evaluacion(unidad);

        // AHORA insert devuelve Evaluacion
        Evaluacion guardada = evaluacionService.insert(evaluacion);

        ModelMapper m = new ModelMapper();
        EvaluacionDto respuesta = m.map(guardada, EvaluacionDto.class);

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // ----------- READ: BUSCAR POR ID -----------
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        Evaluacion evaluacion = evaluacionService.listId(id);
        if (evaluacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una evaluación con ID: " + id);
        }
        return ResponseEntity.ok(evaluacion);
    }

    // ----------- UPDATE -----------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Evaluacion request) {
        Evaluacion existente = evaluacionService.listId(request.getIdEvaluacion());
        if (existente == null) {
            return new ResponseEntity<>("No existe una evaluación con ID: " + request.getIdEvaluacion(), HttpStatus.NOT_FOUND);
        }

        evaluacionService.update(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    // ----------- DELETE -----------
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        Evaluacion evaluacion = evaluacionService.listId(id);
        if (evaluacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una evaluación con ID: " + id);
        }
        evaluacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // ---------- Endpoints para las 4 querys ----------

    // QM #3: duración <= X
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-duracion-max")
    public List<Evaluacion> buscarPorDuracionMaxima(@RequestParam int duracionMax) {
        return evaluacionService.findByDuracionMaxima(duracionMax);
    }

    // SQL nativo #2: FechaInicio > fecha
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-fecha-inicio")
    public List<Evaluacion> buscarPorFechaInicio(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return evaluacionService.findByFechaInicioMayor(fecha);
    }

    // SQL nativo #3: título contiene texto
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-titulo")
    public List<Evaluacion> buscarPorTitulo(@RequestParam String titulo) {
        return evaluacionService.findByTituloContieneSQL(titulo);
    }

    // JPQL #2: entre fechas
    @PreAuthorize("hasAnyRole('ADMIN','ESTUDIANTE')")
    @GetMapping("/buscar-entre-fechas")
    public List<Evaluacion> buscarEntreFechas(
            @RequestParam("inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return evaluacionService.findEntreFechas(inicio, fin);
    }
}
