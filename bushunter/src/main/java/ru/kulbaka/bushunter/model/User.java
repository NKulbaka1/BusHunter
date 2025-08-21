package ru.kulbaka.bushunter.model;

import lombok.Data;
import ru.kulbaka.bushunter.security.model.UserRole;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private UserRole role;
}
