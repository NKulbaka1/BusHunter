package ru.kulbaka.bushunter.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kulbaka.bushunter.security.service.UserService;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class AuthenticationUtils {

    private final UserService userService;

    public Long getCurrentUserId(Principal principal) {
        return userService.getUserId(principal.getName());
    }
}
