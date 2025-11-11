package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.repositories.UsuarioRepository;
import com.example.tp_grupo6.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService
{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> list() {
        return usuarioRepository.findAll();
    }

    @Override
    public void insert(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario listId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void update(Usuario usuario) {
        usuarioRepository.save(usuario);
    }


}
