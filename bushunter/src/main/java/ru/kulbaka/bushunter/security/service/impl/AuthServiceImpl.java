package ru.kulbaka.bushunter.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kulbaka.bushunter.model.User;
import ru.kulbaka.bushunter.security.dao.RefreshTokenDao;
import ru.kulbaka.bushunter.security.dto.AuthRequest;
import ru.kulbaka.bushunter.security.dto.RefreshTokenRequest;
import ru.kulbaka.bushunter.security.dto.RegisterRequest;
import ru.kulbaka.bushunter.security.dto.TokenResponse;
import ru.kulbaka.bushunter.security.model.UserRole;
import ru.kulbaka.bushunter.security.service.AuthService;
import ru.kulbaka.bushunter.security.util.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenDao refreshTokenDao;

    @Override
    public TokenResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        refreshTokenDao.saveRefreshToken(refreshToken, userService.getUserId(userDetails.getUsername()));

        return new TokenResponse(
                accessToken,
                refreshToken
        );
    }

    @Override
    public TokenResponse registerCustomer(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(registerRequest.password());
        user.setFullName(registerRequest.fullName());
        user.setRole(UserRole.ROLE_CUSTOMER);

        userService.createUser(user);

        return authenticate(new AuthRequest(registerRequest.username(), registerRequest.password()));
    }

    @Override
    public TokenResponse registerAdmin(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(registerRequest.password());
        user.setFullName(registerRequest.fullName());
        user.setRole(UserRole.ROLE_ADMIN);

        userService.createUser(user);

        return authenticate(new AuthRequest(registerRequest.username(), registerRequest.password()));
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Невалидный токен");
        }

        if (refreshTokenDao.isTokenRevoked(refreshToken)) {
            throw new IllegalArgumentException("Токен аннулирован");
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        refreshTokenDao.revokeToken(refreshToken);
        refreshTokenDao.saveRefreshToken(newRefreshToken, userService.getUserId(username));

        return new TokenResponse(
                newAccessToken,
                newRefreshToken
        );
    }


}
