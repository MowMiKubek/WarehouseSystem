package com.project.warehouse.service;

import com.project.warehouse.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getOne(long id) throws ChangeSetPersister.NotFoundException;

    public User save(User user);

    public User update(long id, User user) throws ChangeSetPersister.NotFoundException;

    public void delete(long id);
}
