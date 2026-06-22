package com.pharma.auth;

import com.pharma.auth.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit-style test for {@link JwtUtil} without loading the full Spring context.
 * A full {@code @SpringBootTest} would pull in JPA/Flyway and is overridden in CI by
 * {@code SPRING_DATASOURCE_*}, breaking in-memory/H2 setups; this class only needs JWT properties.
 */
class AuthServiceTest {

    private static final String TEST_SECRET = "test-secret-key-must-be-32-characters-long";

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 86_400_000L);
    }

    @Test
    void testTokenGenerationAndValidation() {
        String token = jwtUtil.generateToken("testuser", "ADMIN");
        assertThat(token).isNotBlank();
        assertThat(jwtUtil.validateToken(token)).isTrue();
        assertThat(jwtUtil.getUsernameFromToken(token)).isEqualTo("testuser");
        assertThat(jwtUtil.getRoleFromToken(token)).isEqualTo("ADMIN");
    }
}
