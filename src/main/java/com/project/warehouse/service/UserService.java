package com.project.warehouse.service;

import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.AuthenticationResponse;
import com.project.warehouse.model.dto.LoginDTO;
import com.project.warehouse.model.dto.RegisterDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getOneById(long id) throws ChangeSetPersister.NotFoundException;

    User getOneByLogin(String login) throws ChangeSetPersister.NotFoundException;

    User save(RegisterDTO user);

    User update(long id, RegisterDTO user) throws ChangeSetPersister.NotFoundException;

    void delete(long id);

    AuthenticationResponse login(LoginDTO loginDTO) throws ChangeSetPersister.NotFoundException;

    AuthenticationResponse register(RegisterDTO registerDTO);
}
