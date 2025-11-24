package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion,Integer>
{
    // QM #3: duración menor o igual a X
    List<Evaluacion> findByDuracionLessThanEqual(int duracion);

    // SQL nativo #2: evaluaciones después de una fecha
    @Query(
            value = "SELECT * FROM evaluaciones e WHERE e.fechaInicio > :fecha",
            nativeQuery = true
    )
    List<Evaluacion> buscarPorFechaInicioSQL(@Param("fecha") LocalDate fecha);

    // SQL nativo #3: título que contenga texto
    @Query(
            value = "SELECT * FROM evaluaciones e " +
                    "WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))",
            nativeQuery = true
    )
    List<Evaluacion> buscarPorTituloSQL(@Param("titulo") String titulo);

    // JPQL #2: entre dos fechas
    @Query("SELECT e FROM Evaluacion e " +
            "WHERE e.fechaInicio BETWEEN :inicio AND :fin")
    List<Evaluacion> buscarEntreFechasJPQL(@Param("inicio") LocalDate inicio,
                                           @Param("fin") LocalDate fin);
}
