package com.pharma.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users", schema = "auth")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role {
        ADMIN, PHARMACIST, MANUFACTURER, QC_ANALYST, SUPPLIER, DISTRIBUTOR, VIEWER
    }
}
