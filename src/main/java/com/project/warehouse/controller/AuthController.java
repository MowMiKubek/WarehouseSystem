package com.project.warehouse.controller;

import com.project.warehouse.config.JwtService;
import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.AuthenticationResponse;
import com.project.warehouse.model.dto.LoginDTO;
import com.project.warehouse.model.dto.RegisterDTO;
import com.project.warehouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDTO registerDto
    ) {
        AuthenticationResponse response = userService.register(registerDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDTO registerDto
    ) throws ChangeSetPersister.NotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerDto.getLogin(),
                        registerDto.getPassword()
                )
        );
        User user = userService.getOneByLogin(registerDto.getLogin());
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
