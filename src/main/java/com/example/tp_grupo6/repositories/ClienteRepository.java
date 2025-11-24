package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>
{
    // QUERY METHOD: por email exacto ✅
    List<Cliente> findByEmail(String email);

    // SQL NATIVO: por dominio de email ✅
    @Query(
            value = "SELECT * FROM clientes c WHERE c.email LIKE %:dominio",
            nativeQuery = true
    )
    List<Cliente> buscarPorDominioEmailSQL(@Param("dominio") String dominio);

    // JPQL: edad mínima ✅
    @Query("SELECT c FROM Cliente c WHERE c.edad >= :edadMin")
    List<Cliente> buscarPorEdadJPQL(@Param("edadMin") int edadMin);

    // JPQL: nombre o apellido contiene texto ✅
    @Query("SELECT c FROM Cliente c " +
            "WHERE LOWER(c.Nombre) LIKE LOWER(CONCAT('%', :texto, '%')) " +
            "   OR LOWER(c.Apellido) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Cliente> buscarPorNombreOApellidoJPQL(@Param("texto") String texto);
}
