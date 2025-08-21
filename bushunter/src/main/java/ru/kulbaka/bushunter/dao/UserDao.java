package ru.kulbaka.bushunter.dao;

import ru.kulbaka.bushunter.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);
    Long create(User user);
    boolean existsByUsername(String username);
}
