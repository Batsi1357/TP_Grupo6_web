package com.example.tp_grupo6.security;

import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.repositories.UsuarioRepository;
import com.example.tp_grupo6.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // o tu repo

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));

        var authorities = usuario.getRoles().stream()
                .map(rol -> "ROLE_" + rol.getTipoRol().trim().toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .toList();


        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities
        );
    }
}
