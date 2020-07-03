package com.bolero.boleroteam.service;

import com.bolero.boleroteam.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    void save(User user);
    void remove(Long id);
    void changePassword(User user, String oldPassword, String newPassword);
}
