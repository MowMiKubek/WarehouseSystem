package com.project.warehouse.service;


import com.project.warehouse.config.JwtService;
import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.AuthenticationResponse;
import com.project.warehouse.model.dto.LoginDTO;
import com.project.warehouse.model.dto.RegisterDTO;
import com.project.warehouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceDefault implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOneById(long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public User getOneByLogin(String login) throws NotFoundException {
        return userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
    }

    @Override
    public User save(RegisterDTO userDTO) {
        User user = new User(null, userDTO.getName(), userDTO.getSurname(), userDTO.getLogin(), userDTO.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User update(long id, RegisterDTO userDTO) throws NotFoundException {
        User currentUser = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        currentUser.setName(userDTO.getName());
        currentUser.setSurname(userDTO.getSurname());
        return userRepository.save(currentUser);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public AuthenticationResponse login(LoginDTO loginDTO) throws NotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getPassword()
                )
        );
        User user = userRepository.findByLogin(loginDTO.getLogin())
                .orElseThrow(NotFoundException::new);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse register(RegisterDTO registerDTO) {
        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        User user = save(registerDTO);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
