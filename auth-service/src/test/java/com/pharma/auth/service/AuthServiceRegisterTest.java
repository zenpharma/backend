package com.pharma.auth.service;

import com.pharma.auth.dto.RegisterRequest;
import com.pharma.auth.model.User;
import com.pharma.auth.repository.UserRepository;
import com.pharma.auth.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceRegisterTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtil jwtUtil;
    @InjectMocks private AuthService authService;

    private RegisterRequest request(String username, String email) {
        RegisterRequest r = new RegisterRequest();
        r.setUsername(username);
        r.setEmail(email);
        r.setPassword("Str0ngPass!234");
        return r;
    }

    @Test
    void registerAlwaysAssignsViewerRoleAndHashesPassword() {
        when(userRepository.existsByUsername("lucy")).thenReturn(false);
        when(userRepository.existsByEmail("lucy@example.com")).thenReturn(false);
        when(passwordEncoder.encode("Str0ngPass!234")).thenReturn("hashed-value");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        authService.register(request("lucy", "lucy@example.com"));

        ArgumentCaptor<User> saved = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(saved.capture());
        assertThat(saved.getValue().getRole()).isEqualTo(User.Role.VIEWER);
        assertThat(saved.getValue().getPassword()).isEqualTo("hashed-value");
    }

    @Test
    void registerRejectsDuplicateUsername() {
        when(userRepository.existsByUsername("lucy")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request("lucy", "new@example.com")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username already taken");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerRejectsDuplicateEmail() {
        when(userRepository.existsByUsername("lucy2")).thenReturn(false);
        when(userRepository.existsByEmail("lucy@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request("lucy2", "lucy@example.com")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already registered");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerRequestHasNoRoleField() {
        assertThat(Arrays.stream(RegisterRequest.class.getDeclaredFields()).map(Field::getName))
                .doesNotContain("role");
    }
}
