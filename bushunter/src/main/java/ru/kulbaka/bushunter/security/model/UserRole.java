package ru.kulbaka.bushunter.security.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_CUSTOMER("Покупатель"),
    ROLE_ADMIN("Администратор");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

}
