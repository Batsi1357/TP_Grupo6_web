package com.example.tp_grupo6.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            final String header = request.getHeader("Authorization");
            System.out.println("🔓 [FILTER] URL: " + request.getRequestURI());
            System.out.println("🔓 [FILTER] Header Authorization: " + header);

            String username = null;
            String token = null;

            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
                System.out.println("🔓 [FILTER] Token extraído: " + token.substring(0, 50) + "...");

                try {
                    username = jwtTokenUtil.getUsernameFromToken(token);
                    System.out.println("🔓 [FILTER] Username extraído: " + username);
                } catch (Exception e) {
                    System.out.println("❌ [FILTER] Error extrayendo username: " + e.getMessage());
                }
            } else {
                System.out.println("⚠️ [FILTER] No hay header Authorization o no empieza con 'Bearer '");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("🔓 [FILTER] Validando token para usuario: " + username);

                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtTokenUtil.validateToken(token)) {
                        System.out.println("✅ [FILTER] Token válido. Autenticando a " + username);

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        System.out.println("✅ [FILTER] Autenticación establecida");
                    } else {
                        System.out.println("❌ [FILTER] Token inválido");
                    }
                } catch (Exception e) {
                    System.out.println("❌ [FILTER] Error validando: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("⚠️ [FILTER] Username es null o ya hay autenticación");
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("❌ [FILTER] Error general: " + e.getMessage());
            e.printStackTrace();
            filterChain.doFilter(request, response);
        }
    }
}
