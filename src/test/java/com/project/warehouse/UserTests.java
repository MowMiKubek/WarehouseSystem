package com.project.warehouse;

import com.project.warehouse.model.User;
import com.project.warehouse.model.dto.RegisterDTO;
import com.project.warehouse.service.UserServiceDefault;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTests {
    @Autowired
    private UserServiceDefault userService;

    @Test
    void getUsers_returnsUserEntity_forValidId() throws ChangeSetPersister.NotFoundException {
        User user = userService.getOne(1L);
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Maciej", user.getName());
    }

    @Test
    void getUsers_throwsNotFoundException_forInvalidId() {
        assertThrows(ChangeSetPersister.NotFoundException.class, () ->
            userService.getOne(12));
    }

    @Test
    void registerUser_createsUser_forValidData() throws ChangeSetPersister.NotFoundException {
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
    void registerUser_throwsError_forDuplicateLogin() throws ChangeSetPersister.NotFoundException {
        RegisterDTO user = new RegisterDTO("Tomasz", "Tester", "login", "haslo");
        userService.register(user);
        User newUser = userService.getOneByLogin("login");
        assertNotNull(newUser);
        assertEquals("Tomasz", newUser.getName());
        assertEquals("Tester", newUser.getSurname());
        assertEquals("login", newUser.getLogin());
        assertNotEquals("haslo", newUser.getPassword());
    }
}
