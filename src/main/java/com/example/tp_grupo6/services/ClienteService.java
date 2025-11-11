package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService
{
    public List<Cliente> list();
    public void insert(Cliente cliente);
    public Cliente listId(int id);
    public void delete(int id);
    public void update(Cliente cliente);
    public List<Cliente> findAllByEdad(int Edad);
    public Optional<Cliente> findByEmail(String email);
}
