package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.JwtRequestDto;
import com.example.tp_grupo6.dtos.JwtResponseDto;
import com.example.tp_grupo6.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody JwtRequestDto request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        JwtResponseDto response = new JwtResponseDto(token);
        return ResponseEntity.ok(response);
    }
}
