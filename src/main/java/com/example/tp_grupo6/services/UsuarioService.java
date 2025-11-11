package com.example.tp_grupo6.services;

import com.example.tp_grupo6.entities.Usuario;

import java.util.List;

public interface UsuarioService
{
    public List<Usuario> list();
    public void insert(Usuario usuario);
    public Usuario listId(int id);
    public void delete(int id);
    public void update(Usuario usuario);

}
