package com.project.warehouse.service;


import com.project.warehouse.model.User;
import com.project.warehouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceDefault implements UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(long id, User user) throws NotFoundException {
        User currentUser = userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setSurname(user.getSurname());
                    existingUser.setLogin(user.getLogin());
                    existingUser.setPassword(user.getPassword());
                    return existingUser;
                })
                .orElseThrow(NotFoundException::new);

        return userRepository.save(currentUser);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
