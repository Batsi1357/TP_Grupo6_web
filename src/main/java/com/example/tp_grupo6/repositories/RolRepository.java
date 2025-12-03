package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Integer>
{
    @Query("SELECT r FROM Rol r WHERE r.TipoRol = :tipoRol")
    Optional<Rol> findByTipoRol(@Param("tipoRol") String tipoRol);
}
