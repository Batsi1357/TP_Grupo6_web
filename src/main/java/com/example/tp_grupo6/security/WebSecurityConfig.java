package com.example.tp_grupo6.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
    ) throws Exception {

        http
                // CORS debe ir primero
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Deshabilitar CSRF para APIs REST
                .csrf(csrf -> csrf.disable())
                // Sesiones stateless (sin sesiones de servidor)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Configuración de autorización
                .authorizeHttpRequests(auth ->
                        auth
                                // Rutas públicas - NO requieren autenticación
                                .requestMatchers(HttpMethod.POST, "/Usuario/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/Usuario/insert").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                                // Permitir OPTIONS para CORS preflight
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                // Todas las demás rutas requieren autenticación
                                .anyRequest().authenticated()
                )
                // Manejo de excepciones de autenticación
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                // Deshabilitar login form y basic auth
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        // Agregar filtro JWT antes del filtro de username/password
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuración de CORS personalizada
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir orígenes específicos
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://tp-grupo6-web.onrender.com");

        // Permitir cualquier subdominio de Render (por si el frontend tiene otra URL)
        config.addAllowedOriginPattern("https://*.onrender.com");
        config.addAllowedOriginPattern("http://localhost:*");

        // Métodos HTTP permitidos (especificar todos explícitamente)
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PATCH");

        // Headers permitidos
        config.addAllowedHeader("*");

        // Headers expuestos (para que el frontend pueda leerlos)
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Type");

        // Permitir credenciales (importante para JWT)
        config.setAllowCredentials(true);

        // Tiempo de caché para preflight requests (1 hora)
        config.setMaxAge(3600L);

        // Aplicar esta configuración a todos los endpoints
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
