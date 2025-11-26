package com.example.tp_grupo6.security;

import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        // aquí asumo que tu entidad Usuario tiene getPassword() y getRoles()
        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(
                        usuario.getRoles().stream()
                                .map(rol -> "ROLE_" + rol.getTipoRol()) // o como se llame el campo
                                .toArray(String[]::new)
                )
                .build();
    }
}
