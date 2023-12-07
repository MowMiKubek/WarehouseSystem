package com.project.warehouse;

import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.RegisterDTO;
import com.project.warehouse.service.UserServiceDefault;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTests {
    @Autowired
    private UserServiceDefault userService;

    @Test
    void givenValid_Id_whenGetUsers_returnsUserEntity() throws ChangeSetPersister.NotFoundException {
        User user = userService.getOneById(1L);
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Maciej", user.getName());
    }

    @Test
    void givenInvalidId_whenGetUsers_throwsNotFoundException() {
        assertThrows(ChangeSetPersister.NotFoundException.class, () ->
                userService.getOneById(12));
    }

    @Test
    void forValidData_whenRegisterUser_createsUser() throws ChangeSetPersister.NotFoundException {
        RegisterDTO user = new RegisterDTO("Tomasz", "Tester", "login", "haslo");
        userService.register(user);
        User newUser = userService.getOneByLogin("login");
        assertNotNull(newUser);
        assertEquals("Tomasz", newUser.getName());
        assertEquals("Tester", newUser.getSurname());
        assertEquals("login", newUser.getLogin());
        assertNotEquals("haslo", newUser.getPassword());
    }

    @Test
    void forDuplicateLogin_whenRegisterUser_throwsDataIntegrityException() {
        RegisterDTO user = new RegisterDTO("Tomasz", "Tester", "mmaruda", "haslo");
        assertThrows(DataIntegrityViolationException.class, () -> userService.register(user));
    }
}
