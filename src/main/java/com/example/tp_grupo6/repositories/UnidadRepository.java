package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnidadRepository extends JpaRepository<Unidad, Integer>
{
    // QM #1: por categoría exacta
    List<Unidad> findByCategoria(String categoria);

    // QM #2: por nivel mayor o igual
    List<Unidad> findByNivelGreaterThanEqual(int nivel);

    // SQL nativo #1: por duración mínima
    @Query(
            value = "SELECT * FROM unidades u WHERE u.Duracion >= :minDuracion",
            nativeQuery = true
    )
    List<Unidad> buscarPorDuracionSQL(@Param("minDuracion") int minDuracion);

    // JPQL #1: título que contenga texto
    @Query("SELECT u FROM Unidad u WHERE LOWER(u.titulo) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Unidad> buscarPorTituloJPQL(@Param("texto") String texto);

}
