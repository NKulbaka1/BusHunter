package ru.kulbaka.bushunter.security.service;

import ru.kulbaka.bushunter.model.User;

public interface UserService {
    void createUser(User user);
    Long getUserId(String username);
}
