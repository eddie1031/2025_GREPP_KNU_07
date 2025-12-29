package io.eddie.accountsservice.model.entity;

import io.eddie.accountsservice.model.dto.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code = UUID.randomUUID().toString();

    private String username;
    private String password;

    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @Builder
    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        this.roles.add(Role.MEMBER);

    }

    public void appendRole(Role role) {
        this.roles.add(role);
    }

}
