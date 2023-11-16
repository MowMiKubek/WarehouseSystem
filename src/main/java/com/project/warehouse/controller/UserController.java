package com.project.warehouse.controller;

import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.RegisterDTO;
import com.project.warehouse.service.UserServiceDefault;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceDefault userService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") long id) throws ChangeSetPersister.NotFoundException {
        User user = userService.getOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody() RegisterDTO registerDTO) {
        User newUser = userService.save(registerDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody() RegisterDTO user, @PathVariable("id") long id) throws ChangeSetPersister.NotFoundException {
        User updatedUser = userService.update(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
