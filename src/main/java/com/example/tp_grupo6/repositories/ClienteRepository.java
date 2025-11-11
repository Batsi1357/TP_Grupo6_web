package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>
{
    @Query("select u from Cliente u where lower(u.Nombre) like lower(concat('%', :nombre, '%'))")
    List<Cliente> buscarService(@Param("Nombre") String Nombre);
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findAllByEdad(Integer edad);
}
