package ru.skillbox.booking.service;

import ru.skillbox.booking.model.entity.Role;
import ru.skillbox.booking.model.entity.User;


public interface UserService {
    User findById(Long id);
    User createNewAccount(User user, Role role);
    void deleteById(Long id);
    User findByUsername(String username);
}
